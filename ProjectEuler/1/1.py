# Project Euler Problem 1
# Multiples of 3 and 5
# Sum all the multiples of 3 or 5 below 1000

max = 1000

# simple way
def way1():
    sum = 0
    for i in xrange(1,max):
        if i % 15 == 0:
            sum += i
        elif i % 5 == 0:
            sum += i
        elif i % 3 == 0:
            sum += i

    print sum

# faster way
def way2():
    def sumDivisbleBy(x):
        p = max / x
        return x*(p*(p+1))/2
    print sumDivisbleBy(3) + sumDivisbleBy(5)- sumDivisbleBy(15)

way2()
    
