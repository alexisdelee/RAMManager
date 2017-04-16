package Simultator;

import java.util.ArrayList;

public class RAMManager {
	private long max_size_;
	private long remaining_memory_;
	private long max_block_size_;
	private Boolean full_;
	private ArrayList<Block> memory_ = new ArrayList<Block>();
	
	public RAMManager() {
		this.max_size_ = Utils.memory(128, Utils.BIT);
		this.remaining_memory_ = this.max_size_;
		this.max_block_size_ = Utils.memory(128, Utils.BIT);
		this.full_ = false;
	}
	
	public RAMManager(long max_size) throws InvalidSize {
		if(max_size < Utils.memory(128, Utils.BIT)) throw new InvalidSize();
		
		if((max_size & (max_size - 1)) == 0) { // si c'est une puissance de deux
			this.max_size_ = max_size; // on la stocke
		} else {
			this.max_size_ = max_size - 1; // sinon on récupère la plus haute puissance de deux la plus proche
			this.max_size_ |= this.max_size_ >> 1;
			this.max_size_ |= this.max_size_ >> 2;
			this.max_size_ |= this.max_size_ >> 4;
			this.max_size_ |= this.max_size_ >> 8;
			this.max_size_ |= this.max_size_ >> 16;
			this.max_size_++;
			
			System.out.println("Warning: limited memory size, only " + this.max_size_ + " out of " + max_size + " bits will be used");
		}
		
		this.remaining_memory_ = this.max_size_;
		this.max_block_size_ = Utils.memory(128L, Utils.BIT); // par défaut 128 bits
		this.full_ = false; // au début, tout l'espace est libre
	}
	
	public RAMManager(long max_size, Boolean extend_block_size) throws InvalidSize {
		if(extend_block_size) {
			max_block_size_ = Utils.memory(216L, Utils.BIT);
		} else {
			max_block_size_ = Utils.memory(128L, Utils.BIT);
		}
		
		if(max_size < this.max_block_size_) throw new InvalidSize();
		
		if((max_size & (max_size - 1)) == 0) { // si c'est une puissance de deux
			this.max_size_ = max_size; // on la stocke
		} else {
			this.max_size_ = max_size - 1; // sinon on récupère la plus haute puissance de deux la plus proche
			this.max_size_ |= this.max_size_ >> 1;
			this.max_size_ |= this.max_size_ >> 2;
			this.max_size_ |= this.max_size_ >> 4;
			this.max_size_ |= this.max_size_ >> 8;
			this.max_size_ |= this.max_size_ >> 16;
			this.max_size_++;
			
			System.out.println("Warning: limited memory size, only " + this.max_size_ + " out of " + max_size + " bits will be used");
		}
		
		this.remaining_memory_ = this.max_size_;
		this.full_ = false; // au début, tout l'espace est libre
	}
	
	public long getMax_block_size_() {
		return this.max_block_size_;
	}
	
	public void init(String program, long size_process) throws fullMemory {
		try {			
			if(this.full_) {
				throw new fullMemory();
			} /* else if(this.remaining_memory_ - size_process < 0) {
				System.out.println("Exception: " + program + " program was interrupted; insufficient RAM remaining");
				return;
			} */
			
			Processus process = new Processus(program, size_process);
			if(this.memory_.contains(process)) {
				this.memory_.remove(process);
				
				if(this.remaining_memory_ - size_process < 0) {
					System.out.println("Exception: " + program + " program was interrupted; insufficient RAM remaining");
					return;
				}
			} else if(this.remaining_memory_ - size_process < 0) {
				System.out.println("impossible to create new process: " + program + " [" + size_process + " bits]");
				return;
			}
			
			this.memory_.add(new Block(process, this.max_block_size_));
			this.remaining_memory_ -= size_process;
			
			this.full_ = this.remaining_memory_> 0 ? false : true;
		} catch(InvalidSize e) {
			System.out.println("Exception: invalid format for size");
		}
	}
	
	public void state() {		
		for(Block block: memory_) {
			System.out.println(block.getProcess_() + "[" + Math.round((double)block.getProcess_().getSize_() / this.max_size_ * 100) + "%]");
		}
	}
	
	public void clear() {
		this.memory_.clear();
	}
	
	public String toString() {
		return "RAMManager [" + this.max_size_ + " bits maximum, " + this.max_block_size_ + " bits par bloc, ram remplie: " + this.full_ + "]";
	}
}

class InvalidSize extends Exception {}
