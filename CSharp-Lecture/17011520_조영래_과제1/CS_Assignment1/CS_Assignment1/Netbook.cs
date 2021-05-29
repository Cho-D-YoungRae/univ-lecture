using System;
namespace CS_Assignment1
{
    public class Netbook: Computer
    {
        public const string TYPENAME = "Netbook";
        public const string FUNCS = "internet";
        public const int COST = 7000;

        public readonly int netId;

        public Netbook(int comId, int netId): base(comId)
        {
            this.netId = netId;
        }

        public override string ToString()
        {
            string compInfo = $"type: {Netbook.TYPENAME}, " +
                $"ComId: {this.comId}, " +
                $"NetId: {this.netId}, Used for: " +
                Netbook.FUNCS + ", " +
                $"Avail: " + (this.IsRented() ? "N" : "Y");

            if (this.IsRented())
                compInfo += this.GetRentInfo();

            return compInfo;
        }
    }
}
