package org.cloudbus.cloudsim.edf;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;

public class CloudletWithDeadline extends Cloudlet {

    private double deadline;

    public CloudletWithDeadline(
        int cloudletId,
        long cloudletLength,
        int pesNumber,
        long cloudletFileSize,
        long cloudletOutputSize,
        UtilizationModel utilizationModelCpu,
        UtilizationModel utilizationModelRam,
        UtilizationModel utilizationModelBw,
        double deadline
    ) {
        super(
            cloudletId,
            cloudletLength,
            pesNumber,
            cloudletFileSize,
            cloudletOutputSize,
            utilizationModelCpu,
            utilizationModelRam,
            utilizationModelBw
        );
        this.deadline = deadline;
    }

    public double getDeadline() {
        return deadline;
    }

    public void setDeadline(double deadline) {
        this.deadline = deadline;
    }
}
