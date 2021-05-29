using System;
using System.IO;

namespace CS_assignments2
{
    class Program
    {
        static void Main(string[] args)
        {
            StreamReader sr = new ("INPUT.txt");
            string commandLine = sr.ReadLine(); // 한 줄의 명령어
            string[] commands = commandLine.Split(); // 명령어를 인자별로 나눈다.
            int numWaitingPlaces = int.Parse(commands[0]);  // 대기 장소 수 초기화
            DeliveryVehicleManager deliveryVehicleManager = new(numWaitingPlaces);

            int waitingPlaceId, vehicleId, priority;
            string destination;
            while (sr.Peek() >= 0)
            {
                commandLine = sr.ReadLine();
                commands = commandLine.Split();
                switch (commands[0])
                {
                    case "ReadyIn":
                        waitingPlaceId = int.Parse(commands[1].Substring(1));
                        vehicleId = int.Parse(commands[2]);
                        destination = commands[3];
                        priority = int.Parse(commands[4].Substring(1));
                        deliveryVehicleManager.ReadyIn(
                            waitingPlaceId, vehicleId, destination, priority);
                        break;
                    case "Ready":
                        vehicleId = int.Parse(commands[1]);
                        destination = commands[2];
                        priority = int.Parse(commands[3].Substring(1));
                        deliveryVehicleManager.Ready(
                            vehicleId, destination, priority);
                        break;
                    case "Status":
                        deliveryVehicleManager.Status();
                        break;
                    case "Cancel":
                        vehicleId = int.Parse(commands[1]);
                        deliveryVehicleManager.Cancel(vehicleId);
                        break;
                    case "Deliver":
                        waitingPlaceId = int.Parse(commands[1].Substring(1));
                        deliveryVehicleManager.Deliver(waitingPlaceId);
                        break;
                    case "Clear":
                        waitingPlaceId = int.Parse(commands[1].Substring(1));
                        deliveryVehicleManager.Clear(waitingPlaceId);
                        break;
                    case "Quit":
                        sr.Close();
                        deliveryVehicleManager.Quit();
                        return;
                }
            }
            sr.Close();
            deliveryVehicleManager.Quit();
        }
    }
}
