from operator import mul    # or mul=lambda x,y:x*y
from fractions import Fraction

def nCk(n,k): 
  return int( reduce(mul, (Fraction(n-i, i+1) for i in range(k)), 1) )

for n in range(0,101):
	s = 0
	for j in range(n,101):
		s += nCk(j,n)*((.5)**j)*nCk(100,j)*((.8)**j)*(.2)**(100-j)
	assert abs(s-nCk(100,n)*((0.4)**n)*(0.6)**(100-n)) <= 1e-6