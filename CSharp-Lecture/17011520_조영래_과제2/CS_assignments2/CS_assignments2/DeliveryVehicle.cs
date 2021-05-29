using System;

namespace CS_assignments2
{
    // 우선순위로 정렬하기 위해 IComparable 인터페이스 구현
    public class DeliveryVehicle : IComparable
    {
        //int vehicleid;
        //string destination;
        //int priority;
        // 과제 명세서와는 다르게 아래와 같이 프로퍼티로 구현하였다.
        public int VehicleId { get; init; }
        public string Destination { get; init; }
        public int Priority { get; init; }

        public DeliveryVehicle(int vehicleId, string destination, int priority)
        {
            this.VehicleId = vehicleId;
            this.Destination = destination;
            this.Priority = priority;
        }

        // 배달자동차에 대한 출력을 위해 아래 메소드를 override 해주었다. 
        public override string ToString()
        {
            string toStr = $"FNUM: {VehicleId} " +
                $"DEST: {Destination} PRIO: {Priority}";

            return toStr;
        }

        // 우선순위로 정렬을 하기 위해 IComparable 인터페이스의 아래 메소드 구현
        public int CompareTo(object obj)
        {
            DeliveryVehicle vehicle = obj as DeliveryVehicle;

            return Priority.CompareTo(vehicle.Priority);
        }
    }
}
