import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPortPicker {
    private List<Integer> whitelist;
    private Random random;

    public RandomPortPicker(int k, int[] blacklistedPorts) {
        whitelist = new ArrayList<>();
        random = new Random();

        // Create a set of all ports from 0 to k-1
        List<Integer> allPorts = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            allPorts.add(i);
        }

        // Remove blacklisted ports from the set of all ports
        for (int port : blacklistedPorts) {
            allPorts.remove(Integer.valueOf(port));
        }

        whitelist.addAll(allPorts);
    }

    public int get() {
        // Generate a random index between 0 and the size of the whitelist
        int randomIndex = random.nextInt(whitelist.size());

        // Return the port at the randomly generated index
        return whitelist.get(randomIndex);
    }

    public static void main(String[] args) {
        int[] blacklistedPorts = { 2, 3, 5 };
        RandomPortPicker picker = new RandomPortPicker(7, blacklistedPorts);

        System.out.println(picker.get()); // Return a whitelisted random port
        System.out.println(picker.get()); // Return a whitelisted random port
        System.out.println(picker.get()); // Return a whitelisted random port
        System.out.println(picker.get()); // Return a whitelisted random port
        System.out.println(picker.get()); // Return a whitelisted random port
    }
}
