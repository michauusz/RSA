import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSA {
    private BigInteger n,p,q,e,d,fi;
    private int bitLength = 1024;
    private int certainty = 20;
    private String message, encryptedMessage, decryptedMessage;
    private int maxCharCount = 26;
    private  BigInteger[] x,y,xDecryption;

    public void start(){
        rand();
        //write what was rand
        generateKeys();
        //write was generated
        getText();
        encrypt();
        decrypt();

    }

    private void generateKeys(){
        n = p.multiply(q);
        fi = p.subtract(new BigInteger("1")).multiply((q.subtract(new BigInteger("1"))));
        modularMultiplicativeInverse();

    }

    private void rand(){
      /*  Random rnd = new Random();
        p = new BigInteger(bitLength,  certainty,  rnd);
        q = new BigInteger(bitLength, certainty, rnd);
        e = new BigInteger(bitLength, rnd);
        */
      p = new BigInteger("37");
        q = new BigInteger("47");
        e = new BigInteger("1001");
    }

    private void modularMultiplicativeInverse(){
        int u=0, uPrim=1, v=1,vPrim=0,qInt,r=0,helpUPrim,helpVPrim;
        BigInteger n = fi, a = e;

        while(r!=1){
            qInt = (n.intValue())/(a.intValue());
            r = (n.intValue()) % (a.intValue());
            helpUPrim = uPrim;
            helpVPrim = vPrim;
            uPrim = u;
            vPrim = v;
            u = helpUPrim - (qInt*u);
            v = helpVPrim - (qInt*v);
            n=a;
            a = new BigInteger(String.valueOf(r));


        }
        d = new BigInteger(String.valueOf(v));
        d = d.mod(fi);


    }
    private void getText(){

        Scanner read = new Scanner(System.in);
        System.out.println("Podaj wiadomosc");
        message = read.nextLine();


    }

    private void encrypt(){

        int arrCount =0;
        int messLength = message.length();
        char nul = 0;

        if(messLength%2 == 1) {
            message = message + "a";
            ++messLength;
        }

        x = new BigInteger[messLength/2];
        y = new BigInteger[x.length];
        for(int i=0; i<messLength; i+=2){
            int j=i+1;
            int helpX = ( ((int)message.charAt(i)-97) * power(maxCharCount,1) + ((int)message.charAt(j)-97) * power(maxCharCount, 0));

            x[arrCount] = new BigInteger(String.valueOf(helpX));
           // x[arrCount] = x[arrCount].mod(n);
            ++arrCount;

        }

        for(int i=0; i<arrCount; ++i){
            y[i] = powerModulo(e, x[i] ,n);

        }
        System.out.println("Encrypted message: "  );
        print(y);

            }




    private void print(BigInteger[] text){
        for(int i=0; i<text.length; ++i) System.out.print(text[i]);
    }


    private void decrypt(){
        xDecryption = new BigInteger[y.length];
        for(int i=0; i<y.length;++i){
            xDecryption[i] = powerModulo(d,y[i],n);

        }
        System.out.println("\nDecrypted message");
        for(int i=0; i<xDecryption.length;++i){

                int x2 = (xDecryption[i].mod(new BigInteger(String.valueOf(maxCharCount)))).intValue() + 97;
                int x1 = ((xDecryption[i].divide(new BigInteger("26"))).mod(new BigInteger(String.valueOf(maxCharCount)))).intValue()+97;
                char x1Char = (char)x1;
                char x2Char = (char)x2;
                System.out.print(x1Char);
                System.out.print(x2Char);

        }

    }

    private int power(int a, int b ){

        if(b==0) return 1;
        else return a;



    }








    private BigInteger powerModulo(BigInteger power, BigInteger base, BigInteger mod ){
        String privBinaryString = toBinaryAndReverse(power);
        BigInteger a =  base;
        BigInteger x = new BigInteger("1");
        for(int i=0; i<privBinaryString.length(); ++i){

            if(privBinaryString.charAt(i) == '1') {
                x = x.multiply(a);
                x = x.mod(mod);

            }

            a = a.pow(2);
            a = a.mod(mod);

        }


        return x;
    }

    private String toBinaryAndReverse(BigInteger number){

        StringBuilder input1 = new StringBuilder();
        String privBinaryString = number.toString(2);
        input1.append( privBinaryString);
        input1 =  input1.reverse();
        privBinaryString = input1.toString();


        return privBinaryString;
    }
}
