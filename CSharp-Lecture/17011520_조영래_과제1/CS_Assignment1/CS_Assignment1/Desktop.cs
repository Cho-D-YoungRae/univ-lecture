using System;
namespace CS_Assignment1
{

    public class Desktop: Computer
    {
        public const string TYPENAME = "Desktop";
        public const string FUNCS = "internet, scientific, game";
        public const int COST = 13000;

        public readonly int deskId;

        public Desktop(int comId, int deskId): base(comId)
        {
            this.deskId = deskId;
        }

        public override string ToString()
        {
            string compInfo = $"type: {Desktop.TYPENAME}, " +
                $"ComId: {this.comId}, " +
                $"DeskId: {this.deskId}, Used for: " +
                Desktop.FUNCS + ", " +
                $"Avail: " + (this.IsRented() ? "N" : "Y");

            if (this.IsRented())
                compInfo += this.GetRentInfo();

            return compInfo;
        }
    }
}
