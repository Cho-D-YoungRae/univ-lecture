using System;
namespace CS_Assignment1
{
    public class User
    {
        protected Computer rentComp = null;

        public readonly string name;
        public readonly int userId;


        public User(int userId, string name)
        {
            this.name = name;
            this.userId = userId;
        }

        // 컴퓨터 대여
        public Computer RentComputer(Computer compForRent)
        {
            this.rentComp = compForRent;
            return this.rentComp;
        }

        // 컴퓨터 반납
        public Computer ReturnComputer()
        {
            Computer returnedComp = this.rentComp;
            this.rentComp = null;

            return returnedComp;
        }

        // 현재 대여한 컴퓨터가 있는지 확인
        public bool hasComputer() { return rentComp == null ? false : true; }
    }
}
