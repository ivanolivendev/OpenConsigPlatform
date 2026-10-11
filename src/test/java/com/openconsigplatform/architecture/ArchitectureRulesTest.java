package com.openconsigplatform.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchitectureRulesTest {

    private static JavaClasses applicationClasses;

    @BeforeAll
    static void importApplicationClasses() {
        applicationClasses = new ClassFileImporter()
                .importPackages("com.openconsigplatform");
    }

    @Test
    void domainMustNotDependOnSpring() {
        noClasses()
                .that().resideInAnyPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("org.springframework..")
                .check(applicationClasses);
    }

    @Test
    void domainMustNotDependOnInfrastructure() {
        noClasses()
                .that().resideInAnyPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..infrastructure..")
                .check(applicationClasses);
    }

    @Test
    void applicationMustNotDependOnInterfaces() {
        noClasses()
                .that().resideInAnyPackage("..application..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..interfaces..")
                .check(applicationClasses);
    }

    @Test
    void modulesMustNotDependOnAnotherModuleInfrastructure() {
        noClasses()
                .that().resideInAnyPackage("com.openconsigplatform.access..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("com.openconsigplatform.partners.infrastructure..", "com.openconsigplatform.credit.infrastructure..")
                .check(applicationClasses);

        noClasses()
                .that().resideInAnyPackage("com.openconsigplatform.partners..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("com.openconsigplatform.access.infrastructure..", "com.openconsigplatform.credit.infrastructure..")
                .check(applicationClasses);

        noClasses()
                .that().resideInAnyPackage("com.openconsigplatform.credit..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("com.openconsigplatform.access.infrastructure..", "com.openconsigplatform.partners.infrastructure..")
                .check(applicationClasses);
    }
}
