using System;
namespace CS_Assignment1
{
    public class Computer
    {
        protected User renter = null;
        private int dr = 0, dl = 0;
        public readonly int comId;

        // 외부에서 수정을 하지 못 하도록
        public int DU { get { return this.dr - this.dl; } }
        public int RenterId { get { return this.renter.userId; } }

        public Computer(int comId)
        {
            this.comId = comId;
        }

        // Computer가 반납된 상태인지 확인
        public bool IsRented() { return renter != null ? true : false; }

        // renter에게 dr만큼 대여된다..
        public User RentedTo(User renter, int dr)
        {
            this.renter = renter;
            this.dr = dr;
            this.dl = dr;

            return this.renter;
        }

        // 반남된다.
        public User ReturnedBy()
        {
            User renter = this.renter;
            this.renter = null;

            return renter;
        }

        // 하루 경과
        public void OneDayPass() { this.dl -= 1; }

        // 반납할 날인지 확인
        public bool IsDue() { return dl == 0 ? true : false; }

        // 대여 정보를 반환한다.
        protected string GetRentInfo()
        {
            string rentInfo = $"\n(UserId: {renter.userId}, " +
                    $"DR: {this.dr}, DL: {this.dl}, DU: {this.dr - this.dl})";

            return rentInfo;
        }
        
    }
}
