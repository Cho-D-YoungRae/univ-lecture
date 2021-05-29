using System;

namespace CS_Assignment1
{
    public class ComputerManager
    {

        private Computer[] arrComp;
        private User[] arrUser;
        private readonly int numOfTotalComp;
        private readonly int numOfNetbooks;
        private readonly int numOfNotebooks;
        private readonly int numOfDesktops;        
        private readonly int numOfUsers;
        private int totalCost;

        // 생성자 내에서 Program클래스에 static으로 선언된 sr을 통해 필요한 정보를 읽는다.
        public ComputerManager()
        {
            this.totalCost = 0;

            // arrComp
            this.numOfTotalComp = int.Parse(Program.sr.ReadLine());
            // index 1부터 사용 -> id와 같게 하기위해
            this.arrComp = new Computer[this.numOfTotalComp + 1];   

            string[] numOfEachComputers = Program.sr.ReadLine().Split();
            this.numOfNotebooks = int.Parse(numOfEachComputers[0]);
            this.numOfDesktops = int.Parse(numOfEachComputers[1]);
            this.numOfNetbooks = int.Parse(numOfEachComputers[2]);

            int starti = 1;
            for (int i = starti; i < starti + this.numOfNetbooks; i++)
                this.arrComp[i] = new Netbook(i, i - starti + 1);
            starti += this.numOfNetbooks;
            for (int i = starti; i < starti + this.numOfNotebooks; i++)
                this.arrComp[i] = new Notebook(i, i - starti + 1);
            starti += this.numOfNotebooks;
            for (int i = starti; i < starti + this.numOfDesktops; i++)
                this.arrComp[i] = new Desktop(i, i - starti + 1);

            // arrUser
            this.numOfUsers = int.Parse(Program.sr.ReadLine());
            // index 1부터 사용 -> id와 같게 하기위해
            this.arrUser = new User[this.numOfUsers + 1];
            int numOfWorkers = 0, numOfStudents = 0, numOfGamers = 0;
            for (int i = 1; i <= this.numOfUsers; i++)
            {
                string[] userInfo = Program.sr.ReadLine().Split(" ");
                string userType = userInfo[0], userName = userInfo[1];
                switch (userType)
                {
                    case "Worker":
                        this.arrUser[i] = new Workers(
                            i, ++numOfWorkers, userName);
                        break;
                    case "Student":
                        this.arrUser[i] = new Students(
                            i, ++numOfStudents, userName);
                        break;
                    case "Gamer":
                        this.arrUser[i] = new Gamers(
                            i, ++numOfGamers, userName);
                        break;
                }
            }
        }

        // Workers, Students, Gamers 에 따라 arrComp 어느 index부터 탐색할지
        private int GetCompTypeIdxFor(User user)
        {
            int compTypeIdx = 1;
            if (user is Students)
                compTypeIdx += this.numOfNetbooks;
            if (user is Gamers)
                compTypeIdx += this.numOfNetbooks + this.numOfNotebooks;

            return compTypeIdx;
        }

        // Computer의 type별로 하루 이용 요금을 반환
        private int GetRentalFee(Computer computer)
        {
            if (computer is Netbook)
                return Netbook.COST;
            else if (computer is Notebook)
                return Notebook.COST;
            else
                return Desktop.COST;
        }

        // userId의 User에게 dr 만큼 컴퓨터 대여해준다.
        public bool LendComputerTo(int userId, int dr)
        {
            User renter = this.arrUser[userId];
            if (renter.hasComputer())
                return false;

            for (int i = GetCompTypeIdxFor(renter); i <= this.numOfTotalComp; i++)
            {
                if (this.arrComp[i].IsRented() == false)
                {
                    Computer computer = arrComp[i];
                    computer.RentedTo(renter, dr);
                    renter.RentComputer(computer);

                    Program.sw.WriteLine(
                        $"Computer #{i} has been assigned to User #{userId}");
                    return true;
                }
            }

            return false;
        }

        // userId의 User에게 컴퓨터를 반납받는다.
        public bool GetBackComputerFrom(int userId)
        {
            User renter = this.arrUser[userId];
            if (renter.hasComputer() == false)
                return false;

            Computer returnedComp = renter.ReturnComputer();
            returnedComp.ReturnedBy();

            int rentCost = returnedComp.DU * this.GetRentalFee(returnedComp);
            this.totalCost += rentCost;

            Program.sw.WriteLine($"User #{renter.userId} has returned " +
                $"Computer #{returnedComp.comId} and paid {rentCost} won");

            return true;
        }
        // 하루 시간이 경과된다.
        public void OneDayPass()
        {
            Program.sw.WriteLine("It has passed one day...");
            for (int i = 1; i <= this.numOfTotalComp; i++)
                this.arrComp[i].OneDayPass();

            for (int i = 1; i <= this.numOfTotalComp; i++)
            {
                Computer computer = this.arrComp[i];
                if (computer.IsDue())
                {
                    Program.sw.Write(
                        $"Time for Computer #{i} has expired. ");
                    GetBackComputerFrom(computer.RenterId);
                }
            }
        }
        // 현재 상태를 표사한다.
        public void WriteAllInfo()
        {
            Program.sw.WriteLine($"Total Cost: {this.totalCost}");
            Program.sw.WriteLine("Computer List:");
            // Computer의 자식 클래스 객체들 ToString override함
            for (int i = 1; i <= this.numOfTotalComp; i++)
                Program.sw.WriteLine($"({i}) {this.arrComp[i]}");

            Program.sw.WriteLine("User List:");
            // User의 자식 클래스 객체들 ToString override함
            for (int i = 1; i <= this.numOfUsers; i++)
                Program.sw.WriteLine($"({i}) {this.arrUser[i]}");
        }
    }
}
