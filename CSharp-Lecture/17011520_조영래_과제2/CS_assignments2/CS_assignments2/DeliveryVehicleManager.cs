using System;
using System.Collections;
using System.IO;

namespace CS_assignments2
{
    // 배달 자동차는 IComparable 인터페이스를 구현하여
    // 우선순위로 정렬되도록 하였다.
    public class DeliveryVehicleManager
    {
        //int numWaitingPlaces; // 해당 변수 대신 아래 프로퍼티로 구
        readonly ArrayList[] waitingPlaces;
        readonly StreamWriter sw;
        public int NumWaitingPlaces { get; init; }

        // 배달 자동차 관리시스템 생성자
        // 대기 장소는 ArrayList를 사용하였다.
        public DeliveryVehicleManager(int numWaitingPlaces)
        {
            this.NumWaitingPlaces = numWaitingPlaces;
            sw = new StreamWriter("OUTPUT.txt");

            waitingPlaces = new ArrayList[numWaitingPlaces];
            for (int i = 0; i < numWaitingPlaces; i++)
            {
                waitingPlaces[i] = new ArrayList();
            }
        }

        // 배달자동차를 특정 대기장소에 배정한다.
        public bool ReadyIn(int waitingPlaceId, int vehicleId,
            string destination, int priority)
        {
            // 현재 대기장소 수보다 더 큰 입력이 들어왔을 때 오류 출력
            if (waitingPlaceId > NumWaitingPlaces)
            {
                sw.WriteLine("ReadyIn Error");
                return false;
            }

            int waitPlaceIndex = waitingPlaceId - 1;
            DeliveryVehicle vehicle = new(vehicleId, destination, priority);

            // 대기장소에 배달자동차를 추가한 뒤, 우선순위로 정렬한다.
            waitingPlaces[waitPlaceIndex].Add(vehicle);
            waitingPlaces[waitPlaceIndex].Sort();


            string logStr = $"Vehicle {vehicleId} assigned to waitPlace #{waitingPlaceId}";
            WriteLog(logStr);
            return true;
        }

        // 배달자동차가 가장 적게 대기하고 있는 대기장소에 배달자동차 배정한다.
        public void Ready(int vehicleId, string destination, int priority)
        {
            DeliveryVehicle vehicle = new(vehicleId, destination, priority);

            int minWaitNum = waitingPlaces[0].Count;
            int minWaitIdx = 0;

            // 배달자동차가 가장 적게 대기하고 있는 대기장소를 찾는다.
            for (int i = 1; i < NumWaitingPlaces; i++)
            {
                int waitNum = waitingPlaces[i].Count;
                if (minWaitNum > waitNum)
                {
                    minWaitNum = waitNum;
                    minWaitIdx = i;
                }
            }

            // 해당 대기장소에 배달 자동차 배정 후, 우선순위로 정렬한다. 
            waitingPlaces[minWaitIdx].Add(vehicle);
            waitingPlaces[minWaitIdx].Sort();

            string logStr = $"Vehicle {vehicleId} assigned to waitPlace #{minWaitIdx+1}";
            WriteLog(logStr);
        }

        // 각 대기장소에 대기하고 있는 배달자동차의 정보를 출력한다.
        public void Status()
        {
            WriteLog("************************ Delivery Vehicle Info ************************");
            WriteLog($"Number of WaitPlaces: {NumWaitingPlaces}");
            for (int i = 0; i < NumWaitingPlaces; i++)
            {
                ArrayList waitingPlace = waitingPlaces[i];
                WriteLog($"WaitPlace #{i + 1} Number Vehicles: {waitingPlace.Count}");
                foreach(DeliveryVehicle vehicle in waitingPlace)
                {
                    // 배달자동차에 ToString 메소드를 override 해두었다.
                    WriteLog(vehicle.ToString());
                }
                WriteLog("---------------------------------------------------");
            }
            WriteLog("********************** End Delivery Vehicle Info **********************");
        }

        // 해당 배달자동차를 대기장소에서 삭제한다. 
        public bool Cancel(int vehicleId)
        {
            // 해당하는 배달자동차를 찾아서 삭제한다.
            foreach (ArrayList waitPlace in waitingPlaces)
            {
                for (int i = 0; i < waitPlace.Count; i++)
                {
                    DeliveryVehicle vehicle = waitPlace[i] as DeliveryVehicle;
                    if (vehicle.VehicleId == vehicleId)
                    {
                        waitPlace.RemoveAt(i);
                        string logStr = $"Cancelation of Vehicle {vehicleId} completed.";
                        WriteLog(logStr);
                        return true;
                    }
                }
            }

            // 해당하는 배당자동차가 없을 경우 오류 출력
            WriteLog("Cancel: Error");
            return false;
        }

        // 해당 대기장소에서 대기하고 잇는 배달자동차 중에서
        // 우선순위가 가장 높은 배달자동차를 배달 보낸다.
        public bool Deliver(int waitingPlaceId)
        {
            // 현재 대기장소 수보다 더 큰 입력이 들어왔을 때 오류 출력
            if (waitingPlaceId > NumWaitingPlaces)
            {
                WriteLog("Deliver: WaitingPlace ID Error");
                return false;
            }
            int waitingPlaceIndex = waitingPlaceId - 1;
            ArrayList waitingPlace = waitingPlaces[waitingPlaceIndex];
            // 대기장소에 배달 자동차가 없을 경우 오류 출력
            if (waitingPlace.Count == 0)
            {
                WriteLog("Deliver: WaitingPlace Empty Error");
                return false;
            }

            // 해당 대기장소는 우선순위로 정렬되어있으므로
            // 대기장소의 맨 앞에 배달을 출발해야할 배달자동차가 있다.
            // 해당 배달자동차를 삭제(배달) 한다.
            DeliveryVehicle vehicle = waitingPlace[0] as DeliveryVehicle;
            waitingPlace.RemoveAt(0);
            WriteLog($"Vehicle {vehicle.VehicleId} used to deliver.");
            return true;
        }

        // 해당 대기장소에서 대기하고 있는 배달자동차의 대기를 취소한다.
        public bool Clear(int waitingPlaceId)
        {
            // 현재 대기장소 수보다 더 큰 입력이 들어왔을 때 오류 출력
            if (waitingPlaceId > NumWaitingPlaces)
            {
                WriteLog("Clear: WaitingPlace ID Error");
                return false;
            }
            int waitingPlaceIndex = waitingPlaceId - 1;
            ArrayList waitingPlace = waitingPlaces[waitingPlaceIndex];

            waitingPlace.Clear();
            WriteLog($"WaitPlace #{waitingPlaceId} cleared");
            return true;
        }

        // 프로그램 종료를 위한 일을 한다.
        public void Quit() { sw.Close(); }

        // 결과 출력
        void WriteLog(string logStr)
        {
            sw.WriteLine(logStr);
        }
    }
}
