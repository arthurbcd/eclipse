import java.util.HashMap;

public class Calcul {
	private long nb_ops;
	private HashMap<Long, Long> operands = new HashMap<>();

	public Calcul(Long nb_ops) {
		this.nb_ops = nb_ops;
	}

	public void setOperands(Long pos, Long value) {
		operands.put(pos, value);
	}

	public boolean isComplete() {
		for (long i = 0; i < this.nb_ops; i++) {
			if (!operands.containsKey(i)) {
				return false;
			}
		}
		return true;
	}

	public Long compute() {
		if (!isComplete()) {
			throw new IllegalStateException("Calcul is lacking some operands.");
		}
		long sum = 0;
		for (long i = 0; i < this.nb_ops; i++) {
			sum += operands.get(i);
		}
		return sum;
	}

	public static void main(String[] args) {
		Calcul c = new Calcul(3L);
		c.setOperands(0L, 1L);
		c.setOperands(1L, 2L);
		c.setOperands(2L, 3L);
		System.out.println(c.compute());
	}
}
