package Simultator;

public class Block extends Processus {
	private Long max_block_size_;
	private int block_used_;
	private Processus process_;
	
	public Block(Processus process, Long max_block_size) {		
		this.process_ = process;
		this.max_block_size_ = max_block_size;
		this.block_used_ = (int)Math.ceil((double)this.process_.getSize_() / max_block_size);
	}

	public Processus getProcess_() {
		return process_;
	}

	public void setProcess_(Processus process_) {
		this.process_ = process_;
	}
	
	public long getSizeProcess_() {
		return this.getSize_();
	}
}
