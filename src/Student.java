/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author raghav
 */
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
public class Student
{
    private String name;
    private String schno;
    private int outings;
    private String yc;
    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Student(String name, String schno, int outings, String yc) throws IOException
    {
        this.name = name;
        this.schno = schno;
        this.outings = outings;
        this.yc = yc;
    }

    public Student() throws IOException
    {
        this.name = null;
        this.schno = null;
        outings = 0;
        this.yc = "";
    }

    public String getName()
    {
        return name;
    }

    public String getSchno()
    {
        return schno;
    }

    public String getYc()
    {
        return yc;
    }

    public int getOuting()
    {
        return outings;
    }

    public boolean addOuting(String purpose, String authority) throws IOException
    {
        if(outings>4||yc.equalsIgnoreCase("yes")) return false;
        else 
        {
            outings++;
            addOuting();
            record(purpose,authority);
            return true;
        }
    }

    private void addOuting() throws IOException
    {
        BufferedReader ifile = new BufferedReader(new FileReader("database.txt"));
        FileWriter fileWriter = new FileWriter("temp");
        PrintWriter ofile = new PrintWriter(fileWriter);
        while(true)
        {
            String N = ifile.readLine();
            if(N==null) break;
            String num = ifile.readLine();
            int outing = Integer.parseInt(ifile.readLine());
            String yc = ifile.readLine();
            Student temp = new Student(N,num,outing,yc);
            if(temp.schno.equalsIgnoreCase(schno)) temp.outings = outings;
            ofile.println(temp.name);
            ofile.println(temp.schno);
            ofile.println(temp.outings);
            ofile.println(temp.yc);
        }
        ofile.close();
        ifile.close();
        overwrite("temp","database.txt");
    }
    
    public void revokeYC(Student x) throws IOException
    {
        BufferedReader ifile = new BufferedReader(new FileReader("database.txt"));
        FileWriter fileWriter = new FileWriter("temp");
        PrintWriter ofile = new PrintWriter(fileWriter);
        while(true)
        {
            String N = ifile.readLine();
            if(N==null) break;
            String num = ifile.readLine();
            int outings = Integer.parseInt(ifile.readLine());
            String yc = ifile.readLine();
            Student temp = new Student(N,num,outings,yc);
            if(temp.schno.equals(x.schno)) temp.yc = "No";
            ofile.println(temp.name);
            ofile.println(temp.schno);
            ofile.println(temp.outings);
            ofile.println(temp.yc);
        }
        ofile.close();
        ifile.close();
        overwrite("temp","database.txt");
    }
    
    public void applyYC(Student x) throws IOException
    {
        BufferedReader ifile = new BufferedReader(new FileReader("database.txt"));
        FileWriter fileWriter = new FileWriter("temp");
        PrintWriter ofile = new PrintWriter(fileWriter);
        while(true)
        {
            String N = ifile.readLine();
            if(N==null) break;
            String num = ifile.readLine();
            int outings = Integer.parseInt(ifile.readLine());
            String yc = ifile.readLine();
            Student temp = new Student(N,num,outings,yc);
            if(temp.schno.equals(x.schno)) temp.yc = "Yes";
            ofile.println(temp.name);
            ofile.println(temp.schno);
            ofile.println(temp.outings);
            ofile.println(temp.yc);
        }
        ofile.close();
        ifile.close();
        overwrite("temp","database.txt");
    }

    public Student selectStudent(String schno) throws IOException
    {
        Student x = new Student();
        BufferedReader ifile = new BufferedReader(new FileReader("database.txt"));
        while(true)
        {
            String N = ifile.readLine();
            if(N==null) break;
            String num = ifile.readLine();
            int outing = Integer.parseInt(ifile.readLine());
            String yc = ifile.readLine();
            Student temp = new Student(N,num,outing,yc);
            if(temp.getSchno().equalsIgnoreCase(schno))
            {
                x = temp;
                break;
            }
        }
        return x;
    }

    public boolean unique(Student x) throws IOException
    {
        boolean flag = true;
        try {
        BufferedReader ifile = new BufferedReader(new FileReader("database.txt"));
        String N;
        while(true)
        {
            N = ifile.readLine();
            if(N==null) break;
            String num = ifile.readLine();
            int outing = Integer.parseInt(ifile.readLine());
            String yc = ifile.readLine();
            Student temp = new Student(N,num,outing,yc);
            if(temp.getSchno().equalsIgnoreCase(x.getSchno())&&temp.schno!=null)
            {
                flag = false;
                break;
            }
        }
        ifile.close();
        } catch (FileNotFoundException e)
        {
            FileWriter fileWriter2 = new FileWriter("database.txt");
            PrintWriter ofile2 = new PrintWriter(fileWriter2);
            ofile2.close();
        }
        return flag;
    }

    public void makeEntry() throws IOException
    {
        try
        {
            FileWriter fileWriter = new FileWriter("database.txt",true);
            PrintWriter ofile = new PrintWriter(fileWriter);
            ofile.println(name);
            ofile.println(schno);
            ofile.println(0);
            ofile.println(yc);
            ofile.close();
            FileWriter fileWriter2 = new FileWriter(name+".txt");
            PrintWriter ofile2 = new PrintWriter(fileWriter2);
            ofile2.close();
        }
        catch(FileNotFoundException e)
        {
            FileWriter fileWriter2 = new FileWriter("database.txt");
            PrintWriter ofile2 = new PrintWriter(fileWriter2);
            ofile2.close();
            makeEntry();
        }
    }

    private void record(String purpose, String authority) throws IOException
    {
        FileWriter fileWriter = new FileWriter(name+".txt",true);
        PrintWriter ofile = new PrintWriter(fileWriter);
        ofile.println(getDate());
        ofile.println(purpose);
        ofile.println(authority);
        ofile.close();
    }
    
    public void overwrite(String temp, String original) throws IOException
    {
        BufferedReader ifile = new BufferedReader(new FileReader(temp));
        FileWriter fileWriter = new FileWriter(original);
        PrintWriter ofile = new PrintWriter(fileWriter);
        String N;
        while(true)
        {
            N = ifile.readLine();
            if(N==null) break;
            ofile.println(N);
            ofile.println(ifile.readLine());
            ofile.println(Integer.parseInt(ifile.readLine()));
            ofile.println(ifile.readLine());
        }
        ofile.close();
        ifile.close();
        File file = new File(temp);
        file.delete();
    }

    public String getDate() 
    {
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        String formatted = current.format(formatter);
        return formatted;
    }
}
