package com.example.demo;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packagesOf = DemoApplication.class)
public class PackageDependencyTests {
    @ArchTest // ①
    ArchRule archRule = classes().that().haveNameMatching(".*Repository")
            .should().onlyHaveDependentClassesThat().haveNameMatching(".*Service");

    @ArchTest // ①
    ArchRule archRule2 = classes().that().haveNameMatching(".*Service")
            .should().onlyHaveDependentClassesThat().haveNameMatching(".*Controller");
}
