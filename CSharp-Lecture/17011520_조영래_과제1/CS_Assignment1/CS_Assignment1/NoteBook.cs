using System;
namespace CS_Assignment1
{
    public class Notebook: Computer
    {
        public const string TYPENAME = "Notebook";
        public const string FUNCS = "internet, scientific";
        public const int COST = 10000;

        public readonly int noteId;

        public Notebook(int comId, int noteId) : base(comId)
        {
            this.noteId = noteId;
        }

        public override string ToString()
        {
            string compInfo = $"type: {Notebook.TYPENAME}, " +
                $"ComId: {this.comId}, " +
                $"NoteId: {this.noteId}, Used for: " +
                Notebook.FUNCS + ", " +
                $"Avail: " + (this.IsRented() ? "N" : "Y");

            if (this.IsRented())
                compInfo += this.GetRentInfo();

            return compInfo;
        }
    }
}
