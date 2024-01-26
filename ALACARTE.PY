def ala_carte_multiplication(x, y):
    if x < 0 and y < 0:
        return ala_carte_multiplication(-x, -y)
    if x < 10 or y < 10:
        return x * y

    # Calculate the number of digits of the numbers
    n = max(len(str(x)), len(str(y)))
    m = n // 2

    # Split the numbers into high and low parts
    high_x, low_x = divmod(x, 10**m)
    high_y, low_y = divmod(y, 10**m)

    # Recursive steps
    z0 = ala_carte_multiplication(low_x, low_y)
    z1 = ala_carte_multiplication((low_x + high_x), (low_y + high_y))
    z2 = ala_carte_multiplication(high_x, high_y)

    return (z2 * 10**(2*m)) + ((z1 - z2 - z0) * 10**m) + z0

# Test case
result = ala_carte_multiplication(7000, 7294)
print("Result:", result)
