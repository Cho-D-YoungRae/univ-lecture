using System;
namespace CS_Assignment1
{
    public class Students: User
    {
        const string TYPENAME = "Students";
        const string NEED_FUNCS = "internet, scientific";

        public readonly int studId;

        public Students(int userId, int studId, string name)
            : base(userId, name)
        {
            this.studId = studId;
        }

        public override string ToString()
        {
            string userInfo = $"type: {Students.TYPENAME}, " +
                $"Name: {this.name}, UserId: {this.userId}, " +
                $"WorkerId: {this.studId}, " +
                $"Used for: " + Students.NEED_FUNCS + ", " +
                $"Rent: " + (this.hasComputer() ? "Y" : "N");

            if (this.hasComputer())
                userInfo += "\n(RentCompId: " + this.rentComp.comId + ")";

            return userInfo;
        }
    }
}
