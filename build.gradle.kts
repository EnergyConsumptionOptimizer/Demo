import com.github.gradle.node.npm.task.NpmTask

plugins {
    application
    alias(libs.plugins.node)
}

node {
    version.set("22.19.0")
    download.set(true)
    nodeProjectDir.set(file(project.projectDir))
}

// Dependencies
tasks.register<NpmTask>("npmCi") {
    group = "npm"
    description = "Install npm dependencies cleanly"
    args.set(listOf("ci"))
    inputs.file("package.json")
    inputs.file("package-lock.json")
    outputs.dir("node_modules")
}

// Build
tasks.register<NpmTask>("npmBuild") {
    group = "build"
    description = "Build the TypeScript project"
    dependsOn("npmCi")
    args.set(listOf("run", "build"))
    inputs.dir("src")
    inputs.file("tsconfig.json")
    outputs.dir("dist")
}

tasks.register<NpmTask>("npmTest") {
    group = "quality"
    description = "Test the TypeScript project"
    args.set(listOf("run", "test",">","reports/test-report.txt"))
    outputs.file("reports/test-report.txt")
}

tasks.register<NpmTask>("npmFormat") {
    group = "quality"
    description = "Test the format of TypeScript project"
    args.set(
        listOf(
            "run",
            "format:check",
            ">",
            "reports/format-report.txt",
            "2>&1"
        )
    )
    outputs.file("reports/format-report.txt")
}

tasks.register<NpmTask>("npmLint") {
    group = "quality"
    description = "Test the lint of TypeScript project"
    args.set(
        listOf(
            "run",
            "lint",
            ">",
            "reports/lint-report.txt",
            "2>&1"
        )
    )
    outputs.file("reports/lint-report.txt")
}

tasks.named("assemble") {
    dependsOn("npmBuild")
}

tasks.named<Test>("test") {
    dependsOn("npmTest")
}

tasks.register("lint") {
    dependsOn("npmLint")
}

tasks.register("format") {
    dependsOn("npmFormat")
}