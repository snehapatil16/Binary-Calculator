## Binary Calculator

This project performs basic arithmetic operations (add, subtract, multiply, divide) on binary numbers using only logical/boolean operators, without using mathematical operators.

### Classes
- BitField: Represents a bit-field of a certain number of bits. It wraps around a Boolean array with convenience functions.
- BinaryCalculator: Skeleton code for the project. It contains methods for add, subtract, multiply, and divide operations on binary numbers using logical operators.
- BinaryCalculatorGrader: Generates random bit fields and tests the implemented methods in BinaryCalculator.

### Operations
- Addition: Adds two binary numbers.
- Subtraction: Subtracts one binary number from another.
- Multiplication: Multiplies two binary numbers.
- Division: Divides one binary number by another, returning the quotient and remainder as a BitField array.

### Constraints
- Maximum number of bits: 60
- Division by zero should return null.
- All binary values are considered signed, with the most significant bit (MSB) being the sign-bit.
- Resulting BitField objects must have the same number of bits as the input ones.
