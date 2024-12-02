package org.cloudbus.cloudsim.edf;

import java.text.DecimalFormat;
import java.util.List;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.*;

public class PrintCloudletList {

  public static void print(List<Cloudlet> list) {
    int size = list.size();
    Cloudlet cloudlet;

    System.out.println("\n========== OUTPUT ==========");
    System.out.println(
      "Cloudlet ID\tStatus\tData center ID\tVM ID\tTime\tStart Time\tFinish Time"
    );

    DecimalFormat dft = new DecimalFormat("###.##");
    for (int i = 0; i < size; i++) {
      cloudlet = list.get(i);

      System.out.print(cloudlet.getCloudletId() + "\t\t");

      if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
        System.out.print("SUCCESS\t");
        System.out.print(cloudlet.getResourceId() + "\t\t");
        System.out.print(cloudlet.getVmId() + "\t");
        System.out.print(dft.format(cloudlet.getActualCPUTime()) + "\t");
        System.out.print(dft.format(cloudlet.getExecStartTime()) + "\t\t");
        System.out.print(dft.format(cloudlet.getFinishTime()) + "\t");
      } else {
        System.out.print("FAILED\t");
      }
      System.out.println();
    }
  }
}
