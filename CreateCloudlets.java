package org.cloudbus.cloudsim.edf;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.provisioners.*;

import java.util.ArrayList;
import java.util.List;

public class CreateCloudlets {
  public static List < Cloudlet > create(int brokerId) {
    List < Cloudlet > cloudletList = new ArrayList < > ();

    // Cloudlet parameters
    int numCloudlets = 100; // 100 cloudlets
    long minLength = 10000;
    long maxLength = 50000;
    long minFileSize = 300;
    long maxFileSize = 1000;
    long minOutputSize = 300;
    long maxOutputSize = 1000;
    int pesNumber = 1;

    // Utilization model
    UtilizationModel utilizationModel = new UtilizationModelFull();

    // Generate 100 cloudlets with random parameters
    for (int i = 0; i < numCloudlets; i++) {
      long length = minLength + (long)(Math.random() * (maxLength - minLength)); // Random length between minLength and maxLength
      long fileSize = minFileSize + (long)(Math.random() * (maxFileSize - minFileSize)); // Random file size between minFileSize and maxFileSize
      long outputSize = minOutputSize + (long)(Math.random() * (maxOutputSize - minOutputSize)); // Random output size between minOutputSize and maxOutputSize

      Cloudlet cloudlet = new Cloudlet(
        i, // Cloudlet ID
        length, // Cloudlet length
        pesNumber,
        fileSize,
        outputSize,
        utilizationModel,
        utilizationModel,
        utilizationModel
      );

      cloudlet.setUserId(brokerId);
      cloudletList.add(cloudlet);
    }

    return cloudletList;
  }
}