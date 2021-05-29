using System;
using System.IO;

namespace CS_Assignment1
{
    class Program
    {
        // StreamReader와 StreamWriter를 static으로 선언해서 프로그램 내
        // 어디에서든지 접근할 수 있도록 했다.
        public static StreamReader sr = new StreamReader("input.txt");
        public static StreamWriter sw = new StreamWriter("output.txt");

        static void Main(string[] args)
        {
            // Computer Manager가 생성되면서 생성자 내에서
            // input의 정보를 읽어 필요한 정보를 얻는다.
            ComputerManager computerManager = new ComputerManager();

            // input의 끝까지 읽어들인다.           
            while(Program.sr.Peek() >= 0)
            {
                int userId;
                // input을 통해 명령이 한 줄씩 들어오지만
                // 종종 한 줄에 여러 정보가 들어있을 때가있다
                // ex) A 2 3
                // 이를 위해 명령 문자열을 Split() 해준다.
                string[] commands = Program.sr.ReadLine().Split();
                switch (commands[0])
                {
                    case "Q":   // 전체 프로그램 종료
                        Program.sr.Close();
                        Program.sw.Close();
                        return;
                    case "A":   // 컴퓨터 대여해줌
                        userId = int.Parse(commands[1]); // 대여해줄 사용자 Id
                        int dr = int.Parse(commands[2]); // 대여 일 수
                        computerManager.LendComputerTo(userId, dr); // 대여 메소드
                        break;
                    case "R":   // 반납
                        userId = int.Parse(commands[1]); // 반납하는 사용자 Id
                        computerManager.GetBackComputerFrom(userId); // 반납 메소드
                        break; 
                    case "T":
                        computerManager.OneDayPass(); // 하루 시간 경과 메소드
                        break;
                    case "S":
                        computerManager.WriteAllInfo(); // 현재 상태 표시
                        break;
                }
                Program.sw.WriteLine("===========================================================");
            }

            Program.sr.Close();
            Program.sw.Close();
        }
    }
}

