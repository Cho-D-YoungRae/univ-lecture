using System;
using System.IO;

namespace TestIO
{
    class MainApp
    {
        static void Main(string[] args)
        {
            StreamReader sr = new StreamReader("input.txt");
            StreamWriter sw = new StreamWriter("output.txt");

            string tmpreadline;
            while (sr.Peek() >= 0) // 입력 파일에 더 이상 읽을 문자가 없을 때 까지 실행 
            {
                tmpreadline = sr.ReadLine(); // 입력파일에 한 줄의 문자열을 읽어와 string 변수에 tmpreadline 할당
                sw.WriteLine(tmpreadline);   // tmpreadline를 출력파일에 쓰기
            }
            sr.Close();
            sw.Close();
        }
    }
}
