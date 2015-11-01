package com.everrin.arithmetic;

import android.util.Log;

import java.util.Random;

/**
 * Created by everrin on 9/14/2015.
 */
public class SimpleFormula {
    private int mA;
    private int mB;
    private OPERATOR mOP;

    private int mC; // input result value

    private int mACopy;
    private int mBCopy;
    private OPERATOR mOPCopy;

    public enum OPERATOR {
        ADD(1), SUB(2), MUL(3), DIV(4), RANDOM(5);
        final private int mS;
        OPERATOR(int s)
        {
            mS = s;
        }

        public String toString(){
            switch (mS)
            {
                case 1: return "+";
                case 2: return "-";
                case 3: return "*";
                case 4: return "/";
                case 5: return "RAND";
            }

            return "&";
        }
    }

    public OPERATOR getRandomOperator(OPERATOR[] ops)
    {
        Random r = new Random();
        int a = r.nextInt(ops.length);
        return ops[a];
    }
    public SimpleFormula(int _a, int _b, OPERATOR _op)
    {
        mA = _a; mB = _b; mOP = _op;
    }

    public SimpleFormula(OPERATOR _op)
    {
        mOP = _op;
    }

    public void setOperator(OPERATOR _op)
    {
            mOP = _op;
    }

    public void setInputResult(int c)
    {
        mC = c;
    }

    public boolean checkResult()
    {
        return mC == getResult();
    }

    public String toString()
    {
        return String.format("%d %s %d = ?", mA, mOP.toString(), mB);
    }

    // display the input result. correct or incorrect
    public String toInputString()
    {
        return String.format("%d %s %d = %d", mA, mOP.toString(), mB, mC);
    }

    public int getResult()
    {
        switch (mOP)
        {
            case ADD:
                return mA + mB;
            case SUB:
                return mA - mB;
            case MUL:
                return mA * mB;
            case DIV:
                return mA / mB;
            default:
                return -1;  // never happen
        }
    }

    public void generateNewItem(boolean fixA, int a)
    {

        do{
            if(mOP == OPERATOR.ADD)
            {
                generateAddItem(fixA, a);
            }else
            {
                generateSubItem(fixA, a);
            }
        }while(mACopy == mA && mBCopy == mB && mOP == mOPCopy); // might generate the same formula

        mACopy = mA; mBCopy = mB; mOPCopy = mOP;
    }

    public void generateAddItem(boolean fixA, int a)
    {
        Random r = new Random();
        if(!fixA)
        {
            mA = r.nextInt(9) + 1;
        }else {
            mA = a;
        }
        mB = r.nextInt(9) + 1;
    }

    public void generateSubItem(boolean fixA, int a)
    {
        Random r = new Random();
        if(!fixA)
        {
            mA = r.nextInt(9) + 2;
        }else {
            mA = a;
        }
        mB = r.nextInt(mA - 1) + 1;
    }

    public SimpleFormula CloneFormula()
    {
        SimpleFormula f = new SimpleFormula(this.mA, this.mB, this.mOP);
        f.mC = mC;
        return f;
    }

    public boolean SimpleFormulaEqual(SimpleFormula f)
    {
        return this.mA == f.mA && this.mB == f.mB && this.mOP == f.mOP;
    }

}
