package Simultator;

import java.util.concurrent.*;
import static java.util.concurrent.TimeUnit.*;
import java.lang.Runtime;
import java.io.IOException;

import java.util.ArrayList;

public class BeeperControl {
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private int delay_;
	private int repetition_;
	private static ArrayList<String> program_ = new ArrayList<String>();
	private static long program_size_;
	public static RAMManager ram;
	
	public BeeperControl(int delay, int repetition) {
		this.delay_ = delay;
		this.repetition_ = repetition;
	}
	
	public void start() {
		final Runnable beeper = new Runnable() {
			public void run() {
				try {					
					program_.add("Firefox");
					program_.add("vmware-hostd.exe");
					program_.add("eclipse.exe");
					program_.add("blender.exe");
					program_.add("eclipse.exe");
					program_.add("Cortana");
					program_.add("mysql.exe");
					program_.add("Razer Synapse");
					program_.add("Explorateur Windows.exe");
					program_.add("Windows Shell Experience Host");
					program_.add("Apache HTTP Server");
					
					String he_is_the_elected = program_.get((int)Utils.random(0, program_.size() - 1));
					
					int option = Utils.random(1, 2);
					switch(option) {
						case 1:
							program_size_ = Utils.memory(Utils.random(60, 999), Utils.KILO_BYTE);
						case 2:
							program_size_ = Utils.memory(Utils.random(1, 366), Utils.MEGA_BYTE);
					}
					ram.init(he_is_the_elected, Utils.memory(program_size_, Utils.BIT));
					// ram.kill(process);
					
					ram.state();
				} catch (fullMemory e) {
					System.out.println("Exception: maximum limit of the affected RAM");
				}
			}
		};
		
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, this.delay_, SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() {
				program_.clear(); // effacement de la liste des programmes
				ram.clear(); // effacement de la mémoire gérée par le gestionnaire de RAM
				beeperHandle.cancel(true);
			}
		}, this.repetition_ * this.delay_, SECONDS);
	}
}

class fullMemory extends Exception {}
