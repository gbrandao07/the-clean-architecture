package br.com.brandao.tasks.archunit;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static java.util.stream.Collectors.toList;


@AnalyzeClasses(packages = "br.com.brandao.tasks",
        importOptions = { ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class })
public class CleanArchictetureValidator {

    // entity

    @ArchTest
    static final ArchRule objects_in_entity_package_should_not_use_objects_outside_entity_package =
        classes().that().resideInAPackage("..entity..")
                .should()
                .accessClassesThat()
                .resideInAPackage("..entity..")
                .because("Entity is the most inner layer, so it should depend on any other package");


    @ArchTest
    static final ArchRule objects_in_entity_package_should_be_free_of_annotations =
        classes().that().resideInAPackage("..entity..")
                .should()
                .notBeAnnotatedWith(Repository.class)
                .andShould()
                .notBeAnnotatedWith(Component.class)
                .andShould()
                .notBeAnnotatedWith(Service.class)
                .andShould()
                .notBeAnnotatedWith(Configuration.class)
                .andShould()
                .notBeAnnotatedWith(Controller.class)
                .andShould()
                .notBeAnnotatedWith(Configuration.class)
                .because("Entity layer should not use any Annotations, especially framework's");


    // usecase

    @ArchTest
    static final ArchRule objects_in_usecase_package_should_call_only_objects_in_usecase_and_entity_packages =
        classes()
            .that().resideInAPackage("..usecase..")
            .should(new ArchCondition<>("should call only objects in usecase and entity packages") {
                @Override
                public void check(JavaClass javaClass, ConditionEvents conditionEvents) {
                    javaClass.getAccessesToSelf().forEach(access -> {
                        String targetPackageName = access.getTarget().getOwner().getPackageName();
                        boolean satisfied = false;
                        if (targetPackageName.contains("usecase") || targetPackageName.contains("entity")) {
                            satisfied = true;
                        }
                        conditionEvents.add(new SimpleConditionEvent(access, satisfied, access.getDescription()));
                    });
                }
            })
            .because("Use Case layer should only depend on Entity layer");
    ;


    @ArchTest
    static final ArchRule objects_in_usecase_package_should_be_free_of_annotations =
        classes().that().resideInAPackage("..usecase..")
            .should()
            .notBeAnnotatedWith(Repository.class)
            .andShould()
            .notBeAnnotatedWith(Component.class)
            .andShould()
            .notBeAnnotatedWith(Service.class)
            .andShould()
            .notBeAnnotatedWith(Configuration.class)
            .andShould()
            .notBeAnnotatedWith(Controller.class)
            .andShould()
            .notBeAnnotatedWith(Configuration.class)
            .because("Use Case layer should not use any Annotations, especially framework's");

    @ArchTest
    static final ArchRule input_boundaries_should_implement_one_interactor =
            classes().that().resideInAPackage("..br.com.brandao.tasks.usecase.input.impl..")
                .and()
                .haveSimpleNameEndingWith("Interactor")
                .should()
                .implement(JavaClass.Predicates.resideInAPackage("br.com.brandao.tasks.usecase.input"))
                .because("Interactors should implement Input Boundaries interfaces");


    @ArchTest
    static final ArchRule input_boundaries_should_have_only_one_public_method_and_should_be_called_execute =
            classes().that().resideInAPackage("..br.com.brandao.tasks.usecase.input..")
                .and()
                .areInterfaces()
                .and()
                .haveSimpleNameEndingWith("InputBoundary")
                    .should(new ArchCondition<>("have exactly one public method named 'execute'") {
                        @Override
                        public void check(JavaClass javaClass, ConditionEvents events) {
                            List<JavaMethod> publicMethods = javaClass.getMethods().stream()
                                    //.filter(javaMethod -> javaMethod.getModifiers().contains(PUBLIC))
                                    .collect(toList());
                            boolean satisfied = false;
                            String message = javaClass.getName() + " contains " + publicMethods.size() + " public method";
                            if (publicMethods.size() == 1) {
                                JavaMethod method = publicMethods.get(0);
                                satisfied = method.getName().equals("execute");
                                message += " named '" + method.getName() + "' " + method.getSourceCodeLocation();
                            } else {
                                message += "s " + javaClass.getSourceCodeLocation();
                            }
                            events.add(new SimpleConditionEvent(javaClass, satisfied, message));
                        }
                    })
                    .because("InputBoundaries should have only 'execute' public method");


    @ArchTest
    static final ArchRule interactors_should_have_only_one_public_method_and_should_be_called_execute =
            classes().that().resideInAPackage("..br.com.brandao.tasks.usecase.input.impl..")
                .and()
                .haveSimpleNameEndingWith("Interactor")
                .should(new ArchCondition<>("have exactly one public method named 'execute'") {
                    @Override
                    public void check(JavaClass javaClass, ConditionEvents events) {
                        List<JavaMethod> publicMethods = javaClass.getMethods().stream()
                                .filter(javaMethod -> javaMethod.getModifiers().contains(JavaModifier.PUBLIC))
                                .collect(toList());
                        boolean satisfied = false;
                        String message = javaClass.getName() + " contains " + publicMethods.size() + " public method";
                        if (publicMethods.size() == 1) {
                            JavaMethod method = publicMethods.get(0);
                            satisfied = method.getName().equals("execute");
                            message += " named '" + method.getName() + "' " + method.getSourceCodeLocation();
                        } else {
                            message += "s " + javaClass.getSourceCodeLocation();
                        }
                        events.add(new SimpleConditionEvent(javaClass, satisfied, message));
                    }
                })
                .because("Interactors should have only 'execute' public method overrided by implementing InputBoundary interface");

    // adapter

    @ArchTest
    static final ArchRule objects_in_adapter_dataprovider_package_should_only_implement_gateways_in_usecase_package =
            classes().that().resideInAPackage("..br.com.brandao.tasks.adapter.dataprovider..")
                .and()
                .haveSimpleNameEndingWith("DataProvider")
                .should()
                .implement(JavaClass.Predicates.resideInAPackage("br.com.brandao.tasks.usecase.gateway"))
                .because("DataProviders should implement Gateway interfaces in Use Case layer");


    @ArchTest
    static final ArchRule objects_in_adapter_entrypoint_package_should_only_call_input_boundaries_in_usecase_package =
            classes()
                .that().resideInAPackage("..adapter.entrypoint..")
                .should(new ArchCondition<>("should call only input boundaries in usecase package") {
                    @Override
                    public void check(JavaClass javaClass, ConditionEvents conditionEvents) {
                        javaClass.getAllFields().forEach(access -> {
                            String targetPackageName = access.getType().getName();
                            boolean satisfied = false;
                            if (targetPackageName.contains("usecase.input") ) {
                                satisfied = true;
                            }
                            conditionEvents.add(new SimpleConditionEvent(access, satisfied, access.getDescription()));
                        });
                    }
                })
                .because("Entrypoints should only declare Input Boundaries");
}
