package BinaryCalculator;

public class BinaryCalculator {
   
	public static BitField add(BitField a, BitField b) {
	
    	if(null == a || null == b || a.size() != b.size()){
    		throw new IllegalArgumentException("BinaryCalculator.add(a,b): a and b cannot be null and must be the same length.");
	    }
    	
//    	0+0 → 0
//    	0+1 → 1
//    	1+0 → 1
//    	1+1 → 0, carry 1
    	
    	BitField c = new BitField(a.size());
    	c.setAllFalse();
    	boolean carry = false;
    	
    	for (int i = 0; i < a.size(); i++) {
    		
    		if(a.get(i) == true && b.get(i) == true) { 
    			if (carry == true) { //if both bits are 1 and carry is true
    				c.set(i, true); //setting bit to 1
    				carry = true;
    			} else {
    				c.set(i, false); //setting bit to 0
    				carry = true;
    			}
    	    } else if (a.get(i) == false && b.get(i) == false) { //if carry is 1
    			if (carry == true) {
    				c.set(i, true);
    				carry = false;
    			} 
    	    } else if (a.get(i) == false || b.get(i) == false) {
    	    	if (carry == true) {
    	    		c.set(i, false);
    	    		carry = true;
    	    	} else {
    	    		c.set(i, true);
    	    	}
    		}
    	}
    	return c;
    }

    public static BitField subtract(BitField a, BitField b) {
	
    	if(null == a || null == b || a.size() != b.size()){
    		throw new IllegalArgumentException("BinaryCalculator.add(a,b): a and b cannot be null and must be the same length.");
    	}
    	
    	BitField temp = flipSign(b);
    	
    	return add(a, temp);
    }

    public static BitField multiply(BitField a, BitField b) {
    	
    	if(null == a || null == b || a.size() != b.size()){
    		throw new IllegalArgumentException("BinaryCalculator.add(a,b): a and b cannot be null and must be the same length.");
    	}
    	
    	BitField aCopy = a.copy();
    	BitField bCopy = b.copy();
    	BitField result = new BitField(b.size());
    	BitField zero = new BitField(b.size());
    	
    	while(!aCopy.equals(zero)) {
    		if (aCopy.get(0)) { // if multiplier is 1
    			result = add(result, bCopy); 
    		}
    		bCopy = shiftLeftOne(bCopy); // shift the Multiplicand register left
    		aCopy = shiftRightOne(aCopy); // shift the Multiplicand register right
    	}
    	return result;
    }

	public static BitField[] divide(BitField a, BitField b) {
		
		if (null == a || null == b || a.size() != b.size()) {
			throw new IllegalArgumentException(
					"BinaryCalculator.divide(a,b): a and b cannot be null and must be the same length.");
		}

		boolean z = true;
		for (int i = 0; i < b.size(); i++) {
			if (b.get(i) == true) {
				z = false;
				break;
			}
		}
		// if the divisor is 0, return null
		if (z) {
			return null;
		}

		boolean remSign = a.getMSB(); // if true = negative
		boolean quotientSign = false;
		if (a.getMSB() == b.getMSB()) {
			quotientSign = false;
		} else {
			quotientSign = true;
		}

		// set up the dividend as the absolute value of a - and set it to the remainder
		BitField dividend = absValue(a);
		BitField remainder2x = new BitField(dividend.size() * 2);
		for (int i = 0; i < dividend.size(); i++) {
			remainder2x.set(i, dividend.get(i));
		}

		// set up the divisor as the absolute value of b, double the size, and in the left half of the bits
		BitField absVdivisor = absValue(b);
		BitField divisor = new BitField(absVdivisor.size() * 2);
		for (int i = 0; i < b.size(); i++) {
			divisor.set(i + b.size(), absVdivisor.get(i));
		}

		// set up quotient to be 0 with the regular size of bits
		BitField quotient = new BitField(a.size());

		for (int i = 0; i < a.size() + 1; i++) {
			remainder2x = subtract(remainder2x, divisor); // remainder = remainder - divisor
			if (remainder2x.getMSB() == true) { // if the remainder is negative
				remainder2x = add(remainder2x, divisor);
				quotient = leftShift(quotient, 1);
				quotient.set(0, false);
			} else { // if the remainder is positive
				quotient = leftShift(quotient, 1);
				quotient.set(0, true);
			}
			divisor = rightShift(divisor, 1);
		}

		// get the number of bits of the remainder to the original size
		BitField remainder = new BitField(quotient.size());
		for (int i = 0; i < remainder.size(); i++) {
			remainder.set(i, remainder2x.get(i));
		}
		// negating the remainder if the signed bits differed
		if (remSign) {
			remainder = flipSign(remainder);
		}
		// negating the final quotient if the signed bits differed
		if (quotientSign) {
			quotient = flipSign(quotient);
		}
		// return both the quotient and the remainder
		BitField[] out = new BitField[2];
		out[0] = quotient; // quotient
		out[1] = remainder; // remainder
		return out;
	}
    
    public static BitField shiftLeftOne(BitField a) {
    	
        BitField out = new BitField(a.size());
       
    	for (int i = 1; i < a.size(); i++) {
    		out.set(i, a.get(i-1));
    	}
    	return out;
    }
    
    public static BitField shiftRightOne(BitField a) {
    	
        BitField out = new BitField(a.size());
      
    	for(int i = 1; i < a.size(); i++) {
    		out.set(i-1, a.get(i));
    	}
    	return out;
    }
    
    public static BitField flipSign(BitField a) {
    	
    	BitField temp = complement(a);
    	BitField one = new BitField(a.size());
    	one.set(0,  true);
    	
    	return add(temp, one);
    }
    
    public static BitField complement(BitField a) {
    	
    	BitField out = new BitField(a.size());
    	for (int i = 0; i < a.size(); i++) {
    		out.set(i, !a.get(i));
    	}
    	return out;
    }
}