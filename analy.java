import java.io.*;

public class analy {
    public static char next=0;
    public static boolean isDigital(char c){
        if(c>='0' && c<='9') return true;
        else return false;
    }
    public static boolean isInt(String s){
        if(s.length()==0) return false;
        for (char c:s.toCharArray()) {
            if(!isDigital(c)) return false;
        }
        return true;
    }
    public static void print(String s,int type){
        String anss="";
        if(type==0) anss="Ident("+s+")";
        else if(type==1){
            if(s.length()==1);
            else {
                int pos=-1;
                for(int i=0;i<s.length();i++){
                    if(s.toCharArray()[i]=='0') pos=i;
                    else break;
                }
                if(pos==s.length()-1) pos--;
                s=s.substring(pos+1);
            }
            anss="Int("+s+")";
        }
        else if(type==2) anss="Begin";
        else if(type==3) anss="End";
        else if(type==4) anss="If";
        else if(type==5) anss="Then";
        else if(type==6) anss="Else";
        else if(type==7) anss="Plus";
        else if(type==8) anss="Star";
        else if(type==9) anss="Colon";
        else if(type==10) anss="Comma";
        else if(type==11) anss="LParenthesis";
        else if(type==12) anss="RParenthesis";
        else if(type==13) anss="Assign";
        else if(type==14) anss="For";
        System.out.println(anss);
        return;
    }
    public static boolean check(String s){
        int l=s.length();
        boolean flag=false;
        String before="";
        String after="";
        String[] tb={"FOR","BEGIN","END","IF","ELSE","THEN","+","*",":=",":",",","(",")"};
        int l2=tb.length;
        int i;
        for (i=0;i<l2;i++){
            if(s.contains(tb[i])){
                before=s.substring(0,l-tb[i].length());
                after=tb[i];
                flag=true;
                break;
            }
        }
        if(flag){
            if(before.length()>0) match(before);
            match(after);
            return true;
        }
        return false;
    }
    public static void match(String tmps){
        if(tmps.length()==0) return;
        if(isInt(tmps)) print(tmps,1);
        else if(tmps.equals("BEGIN")) print(tmps,2);
        else if(tmps.equals("END")) print(tmps,3);
        else if(tmps.equals("IF")) print(tmps,4);
        else if(tmps.equals("THEN")) print(tmps,5);
        else if(tmps.equals("ELSE")) print(tmps,6);
        else if(tmps.equals("+")) print(tmps,7);
        else if(tmps.equals("*")) print(tmps,8);
        else if(tmps.equals(":")) print(tmps,9);
        else if(tmps.equals(",")) print(tmps,10);
        else if(tmps.equals("(")) print(tmps,11);
        else if(tmps.equals(")")) print(tmps,12);
        else if(tmps.equals(":=")) print(tmps,13);
        else if(tmps.equals("FOR")) print(tmps,14);
        else print(tmps,0);
        return;
    }
    public static boolean ckvalid(char c){
        if((c>='0' && c<='9') || (c>='a' && c<='z') || (c>='A' && c<='Z') || c==':' || c=='+' || c=='*' || c==',' || c=='(' || c==')' || c=='=') return true;
        return false;
    }
    public static void main(String[] args){
        String p1="in.txt";
        String p2=args[0];
        File file=new File(p2);
        Reader r=null;
        try {
            r=new InputStreamReader(new FileInputStream(file));
            int tmp;
            String tmps="";
            char ls=0;
            boolean flag=true;
            boolean nflag=true;
            while((tmp=r.read())!=-1){
                char c=(char) tmp;
                if((c>='0' && c<='9') || (c>='a' && c<='z') || (c>='A' && c<='Z') || c==':' || c=='+' || c=='*' || c==',' || c=='(' || c==')'){
                    if(isInt(tmps) && !isDigital(c)){
                        print(tmps,1);
                        tmps="";
                    }
                    //System.out.println("here1"+flag);
                    tmps+=c;
                    if(c==':'){
                        if((tmp=r.read())!=-1){
                            next=(char) tmp;
                            if(next=='='){
                                tmps+=next;
                                check(tmps);
                                tmps="";
                            }
                            else {
                                check(tmps);
                                if(ckvalid(next)) tmps=""+next;
                                else if(next==' ' || next=='\n' || next=='\r') tmps="";
                                else{
                                    flag=false;
                                    break;
                                }
                            }
                        }
                        else{
                            check(tmps);
                            break;
                        }
                    }
                    else {
                        nflag=check(tmps);
                        if(nflag) tmps="";
                    }
                    //System.out.println("here2"+flag);
                }
                else if(c==' ' || c=='\n' || c=='\r'){
                    match(tmps);
                    tmps="";
                }
                else{
                    match(tmps);
                    tmps="";
                    flag=false;
                    break;
                }
                ls=c;
            }
            if(!tmps.equals("") && !tmps.equals(":")){
                flag=true;
                match(tmps);
            }
            if(!flag) System.out.println("Unknown");
        }catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            int tmp;
//            r=new InputStreamReader(new FileInputStream(file));
//            while((tmp=r.read())!=-1){
//                System.out.println((char) tmp);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }
}
