/* 
 * Copyright 2006, United States Government as represented by the Administrator
 * for the National Aeronautics and Space Administration. No copyright is
 * claimed in the United States under Title 17, U.S. Code. All Other Rights
 * Reserved. 
 */
package gov.nasa.ial.mde.solver.symbolic;

public class Action {
    
    /** An Operator. */
    public static final int CORRUPTED = -2,
                            NO_OP = -1,
                            U_MINUS = 0,
                            SUM = 1,
                            RECIPROCAL = 2,
                            PRODUCT = 3,
                            POWER = 4,
                            SQRT = 5,
                            EXPONENTIAL = 6,
                            LOG = 7,
                            SINE = 8,
                            COSINE = 9,
                            TANGENT = 10,
        // NBT Assignment of new functions to numbers
        // Fixed ROS: This is logically fine, but the convention is that final static fields should be all caps
        ARCSINE = 11,
        ARCCOSINE = 12,
        ARCTANGENT = 13,
        SECANT = 14,
        COSECANT = 15,
        COTANGENT = 16,
        ARCSECANT = 17,
        ARCCOSECANT = 18,
        ARCCOTANGENT = 19,
        // NBT reassignment of abs
        ABS = 20,
                            FIRST_FUNCTION = SQRT;

    // Fixed ROS: You need to add in your new functions here, e.g. after new TangentObject, add New ArcsineObject, etc.  Otherwise, 
    // there is no way for the rest of the program to reference your implementations.
    // Note that this list corresponds to the final static ints above, thus, the order should be the same.
    /** Evaluator object. */
    static final OperatorObject[] EVALUATOR = {
                            new UnaryMinusObject(),
                            new SumObject(),
                            new ReciprocalObject(),
                            new ProductObject(),
                            new PowerObject(),
                            new SqrtObject(),
                            new ExpObject(),
                            new LogObject(),
                            new SineObject(),
                            new CosineObject(),
                            new TangentObject(),
            new ArcsineObject(),
            new ArccosineObject(),
            new ArctangentObject(),
            new SecantObject(),
            new CosecantObject(),
            new CotangentObject(),
            new ArcsecantObject(),
            new ArccosecantObject(),
            new ArccotangentObject(),
                            new AbsObject() };

    // Fixed ROS: Similarly, you need to add in the Strings that you want to 
    // use for the names of the new functions.  These will
    // be the names of the functions that MathTrax will recognize in equations entered by the user.
    /** Symbolic function name. */
    final static String[] FNAMES = { "-", "+", "/", "*", "^", "sqrt", "exp", "log", "sin", "cos", "tan", "asin", "acos", "atan", "sec", "csc", "cot", "asec", "acsc", "acot", "abs" };

    /**
     * Constructs an Action.
     */
    public Action() {
        super();
    }

    /**
     * Returns the index to the first match in the list to the target.
     * 
     * @param target the tartget string to match.
     * @param list the list of string to compare against.
     * @return the index to the first match in the list.
     */
    public int findFirst(String target, String[] list) {
        // System.out.println ("Target = "+target);
        for (int i = 0; i < list.length; i++) {
            // System.out.println ("Checking "+list[i]);
            if (target.startsWith(list[i])) {
                return i;
            }
        } // end for i

        return NO_OP;
    } // end findFirst
    
} // end class Action

class OperatorObject {
    double eval(ParseNode[] values) {
        return 0.0;
    } // end eval
} // end operatorObjectk

class UnaryMinusObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return -values[0].eval();
    } // end eval
} // end class UnaryMinusObject

class SumObject extends OperatorObject {
    double eval(ParseNode[] values) {
        double s = 0.0;

        for (int i = 0; i < values.length; i++)
            s += values[i].eval();
        return s;
    } // end eval
} // end class SumObject

class ReciprocalObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return 1.0 / values[0].eval();
    } // end eval
} // end reciprocalObjec

class ProductObject extends OperatorObject {
    double eval(ParseNode[] values) {
        double p = 1.0;

        for (int i = 0; i < values.length; i++)
            p *= values[i].eval();
        return p;
    } // end eval
} // end ProductObject

class PowerObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return Math.pow(values[0].eval(), values[1].eval());
    } // end eval
} // end PowerObject

class SqrtObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return Math.sqrt(values[0].eval());
    } // end eval
} // end SqrtObject

class ExpObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return Math.exp(values[0].eval());
    } // end eval
} // end ExpObject

class LogObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return Math.log(values[0].eval());
    } // end eval
} // end LogObject

class SineObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return Math.sin(values[0].eval());
    } // end eval
} // end SineObject

class CosineObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return Math.cos(values[0].eval());
    } // end eval
} // end CosineObject

class TangentObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return Math.tan(values[0].eval());
    } // end eval
} // end TangentObject

// NBT implementation of arcsine
class ArcsineObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return Math.asin(values[0].eval());
    } // end eval
} // end ArcsineObject

// NBT implementation of arccosine
class ArccosineObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return Math.acos(values[0].eval());
    } // end eval
} // end ArccosineObject

// NBT implementation of arctangent
class ArctangentObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return Math.atan(values[0].eval());
    } // end eval
} // end Arctangent Object
// NBT implementation of cosecant
class CosecantObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return 1/Math.sin(values[0].eval());
    } // end eval
} // end CosecantObject

// NBT implementation of Secant
class SecantObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return 1/Math.cos(values[0].eval());
    } // end eval
} // end SecantObject

// NBT implementation of Cotangent
class CotangentObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return 1/Math.tan(values[0].eval());
    } // end eval
} // end Cotangent Object

// NBT implementation of arccosecant
class ArccosecantObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return Math.asin(1.0/values[0].eval());
    } // end eval
} // end ArccosecantObject

// NBT implementation of arcsecant
class ArcsecantObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return Math.acos(1.0/values[0].eval());
    } // end eval
} // end ArcsecantObject

// NBT implementation of arccotangent
class ArccotangentObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return Math.atan(1.0/values[0].eval());
    } // end eval
} // end Arccotangent Object


class AbsObject extends OperatorObject {
    double eval(ParseNode[] values) {
        return Math.abs(values[0].eval());
    } // end eval
} // end AbsObject
