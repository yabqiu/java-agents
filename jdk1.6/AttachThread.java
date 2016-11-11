import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;

public class AttachThread extends Thread {
    private final String jar;
    private List<VirtualMachineDescriptor> listBefore;

    private AttachThread(String attachJar, List<VirtualMachineDescriptor> vms) {
        listBefore = vms;
        jar = attachJar;
    }

    public void run() {
        VirtualMachine vm = null;
        List<VirtualMachineDescriptor> listAfter = null;

        try {
            int count = 0;
            while(true) {
                listAfter = VirtualMachine.list();
                for( VirtualMachineDescriptor vmd: listAfter) {
                    if(!listBefore.contains(vmd)) {
                        vm = VirtualMachine.attach(vmd);
                        break;
                    }
                }
                Thread.sleep(500);
                count ++;
                if(vm != null || count >= 100) {
                    break;
                }
            }
            if(vm != null) {
                vm.loadAgent(jar);
                vm.detach();
            } else {
                System.out.println("new VM not started in time");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AttachThread("test-agent.jar", VirtualMachine.list()).start();
    }
}
