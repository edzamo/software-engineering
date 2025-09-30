# project-tdd

## Summary of "Understanding software testing"
https://medium.com/@netxm/how-to-get-started-with-software-testing-9fa1ce4f2a64


Software testing is an investigation conducted to provide stakeholders with information about the quality of the software product or service under test. It's a crucial process to avoid bugs and ensure the software is fit for use. Developers should have a solid testing strategy.

There are different types of bugs like business logic, security, regression, performance, accessibility, UI, and integration bugs. Testing can be approached as white-box (inspecting source code) or black-box (testing external interface).

The test pyramid is a concept that suggests having many unit tests, fewer integration tests, and very few end-to-end tests. The main levels of testing are:

*   **Unit testing:** Testing isolated units of code.
*   **Integration testing:** Verifying how two or more components work together.
*   **System testing (end-to-end):** Testing the system as a whole from a user's perspective.
*   **Acceptance testing:** Evaluating the system’s compliance with business requirements.

Different types of tests include functional, non-functional, performance, security, regression, and smoke testing. Static analysis can also help in preventing bugs. A good understanding of these concepts helps in creating an effective testing strategy.

## What is Test-Driven Development (TDD)?

Test-Driven Development (TDD) is a software development process that relies on the repetition of a very short development cycle:

1.  **Red:** Write a test for a new piece of functionality. This test will fail initially because the functionality doesn't exist yet.
2.  **Green:** Write the simplest possible code to make the test pass.
3.  **Refactor:** Clean up the code you just wrote, while ensuring the tests still pass.

This cycle is often referred to as "Red-Green-Refactor".

## Why should we use TDD?

Adopting TDD offers several significant benefits:

*   **Higher Code Quality:** By thinking about the requirements from a testing perspective first, you are forced to design more modular, decoupled, and testable code.
*   **Safety Net for Refactoring:** A comprehensive suite of tests gives you the confidence to make changes and refactor your code, knowing that if you break something, a test will fail.
*   **Living Documentation:** The tests themselves act as a form of documentation, clearly describing how the system is supposed to work.
*   **Reduces Bugs:** Writing tests first helps catch bugs early in the development process, making them easier and less costly to fix.
*   **Drives Development:** The failing tests guide your development process, ensuring you only write code that is necessary to meet the requirements.
