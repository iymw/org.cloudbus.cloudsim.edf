package org.cloudbus.cloudsim.edf;

import java.util.Calendar;
import java.util.List;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.*;

public class Main {

  public static void main(String[] args) {
    try {
      // Number of cloud users
      int num_user = 1;

      // Calendar instance
      Calendar calendar = Calendar.getInstance();

      // Trace flag (set to true for detailed tracing)
      boolean trace_flag = false;

      // Initialize CloudSim
      CloudSim.init(num_user, calendar, trace_flag);

      // Create Datacenter
      Datacenter datacenter0 = CreateDataCenter.create("Datacenter_0", 50);

      // Create Broker
      DatacenterBroker broker = createBroker();
      int brokerId = broker.getId();

      // Create VMs
      List<Vm> vmList = CreateVirtualMachines.create(brokerId);

      // Create Cloudlets
      List<Cloudlet> cloudletList = CreateCloudlets.create(brokerId);

      // Associate Cloudlets with VMs
      for (int i = 0; i < cloudletList.size(); i++) {
        Cloudlet cloudlet = cloudletList.get(i);
        // Assign cloudlet to the first VM (or you can add more logic to balance load)
        cloudlet.setVmId(vmList.get(i % vmList.size()).getId()); // Assign each cloudlet to a VM in round-robin manner
      }

      // Submit VM and Cloudlet lists to broker
      broker.submitVmList(vmList);
      broker.submitCloudletList(cloudletList);

      // Start the simulation
      CloudSim.startSimulation();

      // Stop the simulation
      CloudSim.stopSimulation();

      // Final result processing
      List<Cloudlet> newList = broker.getCloudletReceivedList();
      PrintCloudletList.print(newList);
    } catch (Exception e) {
      System.out.println("Simulation terminated due to unexpected error:");
      e.printStackTrace();
    }
  }

  /**
   * Creates a broker
   * @return DatacenterBroker instance
   */
  private static DatacenterBroker createBroker() {
    DatacenterBroker broker = null;
    try {
      broker = new DatacenterBroker("Broker");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return broker;
  }
}
