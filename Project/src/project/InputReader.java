
package project;

import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputReader
	{

		private InputStream stream;
		private byte[] buf = new byte[8192];
		private int curChar, snumChars;
		private SpaceCharFilter filter;

		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}

		public int snext()
		{
			if (snumChars == -1)
				throw new InputMismatchException();
			if (curChar >= snumChars)
			{
				curChar = 0;
				try {
					snumChars = stream.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (snumChars <= 0)
					return -1;
			}
			return buf[curChar++];
		}

		public int nextInt()
		{
			int c = snext();
			while (isSpaceChar(c))
				c = snext();
			int sgn = 1;
                        while(c == '#')
                        {
                            while(c!='\n')
                                c = snext();
                            c=snext();
                        }
			while (isSpaceChar(c))
				c = snext();
			if (c == '-')
			{
				sgn = -1;
				c = snext();
			}
			int res = 0;
			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = snext();
			} while (!isSpaceChar(c));
			return res * sgn;
		}

		public long nextLong() {
			int c = snext();
			while (isSpaceChar(c))
				c = snext();
			int sgn = 1;
			if (c == '-')
			{
				sgn = -1;
				c = snext();
			}
			long res = 0;
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = snext();
			} while (!isSpaceChar(c));
			return res * sgn;
		}
		public String readString()
		{
			int c = snext();
			while (isSpaceChar(c))
				c = snext();
                        while(c == '#')
                        {
                            while(c!='\n')
                                c = snext();
                            c=snext();
                        }
			while (isSpaceChar(c))
				c = snext();
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = snext();
			} while (!isSpaceChar(c));
			return res.toString();
		}
                public int nextAxes()
		{
			int c = snext();
			while (isSeparate(c))
				c = snext();
			int sgn = 1;
                        while(c == '#')
                        {
                            while(c!='\n')
                                c = snext();
                            c=snext();
                        }
			while (isSeparate(c))
				c = snext();
			if (c == '-')
			{
				sgn = -1;
				c = snext();
			}
			int res = 0;
			do
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = snext();
			} while (!isSeparate(c));
			return res * sgn;
		}

                public Point readPoint(){
                    Point p=new Point();
                    int x=nextAxes();
                    int y=nextAxes();
                    p.x=x;
                    p.y=y;                    
                    return p;
                }
                public Module readSoftModule(int n){
                    String name=readString();
                    readString();
                    double area=Double.parseDouble(readString());
                    Module m=new Module(n ,name, area);
                    int c=snext();
                    while(!isNextLine(c)){
                        c=snext();
                    }
                    return m;
                }
                public Module readHardModule(int n){
                    String name=readString();
                    readString();
                    int num=nextInt();
                    Point p[]=new Point[num];
                    for(int j=0;j<num;j++){
                        p[j]=readPoint();
                    }
                    double area=computeArea(p);
                    Module m=new Module(n ,name, area);
                    int c=snext();
                    while(!isNextLine(c)){
                        c=snext();
                    }
                    return m;
                }
                public double computeArea(Point p[]){
                    double area=0;
                    int l=p.length;
                    for(int i=0;i<l;i++){
                        int j=(i+1)%l;
                        int t=p[i].x*p[j].y-p[i].y*p[j].x;
                        area+=t;
                    }
                    area=Math.abs(area);
                    area/=2.0;
                    //System.out.println(area);
                    return area;
                }
                public String readBlock(){
                    String name=readString();
                    int c=snext();
                    while(!isNextLine(c)){
                        c=snext();
                    }
                    return name;
                }
                public double nextDouble(){
                    String s=readString();
                    double n=Double.parseDouble(s);
                    return n;
                }
		public boolean isSpaceChar(int c)
		{
			if (filter != null)
				return filter.isSpaceChar(c);
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
                
                public boolean isSeparate(int c)
		{
			if (filter != null)
				return filter.isSpaceChar(c);
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1 || c == ',' || c == '(' || c == ')';
		}
                public boolean isNextLine(int c)
		{
			if (filter != null)
				return filter.isSpaceChar(c);
			return c == '\n';
		}
		public interface SpaceCharFilter
		{
			public boolean isSpaceChar(int ch);
                        public boolean isSeparate(int ch);
                        public boolean isNextLine(int ch);
		}
                public void close(){
                    try {
                        stream.close();
                    } catch (IOException ex) {
                        Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
	}


