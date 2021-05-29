using System;
namespace CS_Assignment1
{
    public class Gamers: User
    {
        const string TYPENAME = "Gamers";
        const string NEED_FUNCS = "internet, game";

        public readonly int gamerId;

        public Gamers(int userId, int gamerId, string name)
            : base(userId, name)
        {
            this.gamerId = gamerId;
        }

        public override string ToString()
        {
            string userInfo = $"type: {Gamers.TYPENAME}, " +
                $"Name: {this.name}, UserId: {this.userId}, " +
                $"WorkerId: {this.gamerId}, " +
                $"Used for: " + Gamers.NEED_FUNCS + ", " +
                $"Rent: " + (this.hasComputer() ? "Y" : "N");

            if (this.hasComputer())
                userInfo += "\n(RentCompId: " + this.rentComp.comId + ")";

            return userInfo;
        }
    }
}
