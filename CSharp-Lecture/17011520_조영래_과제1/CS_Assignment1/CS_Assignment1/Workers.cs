using System;
namespace CS_Assignment1
{
    public class Workers: User
    {
        const string TYPENAME = "OfficeWorkers";
        const string NEED_FUNCS = "internet";

        public readonly int workerId;

        public Workers(int userId, int workerId, string name)
            : base(userId, name)
        {
            this.workerId = workerId;
        }

        public override string ToString()
        {
            string userInfo = $"type: {Workers.TYPENAME}, " +
                $"Name: {this.name}, UserId: {this.userId}, " +
                $"WorkerId: {this.workerId}, " +
                $"Used for: " + Workers.NEED_FUNCS + ", " +
                $"Rent: " + (this.hasComputer() ? "Y" : "N");

            if (this.hasComputer())
                userInfo += "\n(RentCompId: " + this.rentComp.comId + ")";

            return userInfo;
        }
    }
}
