package assignment;

public class AList<T> implements ListInterface<T> {

	private T[] list;

	private int numberOfEntries;

	// constant for the default capacity
	private static final int DEFAULT_CAPACITY = 25;

	// Step 2.1 - Add the new data fields
	// Set a limit on the maximum number of entries a single Bag can hold
	private static final int MAX_CAPACITY = 10000;

	// Step 3.1
	// Set the default value of the integrity flag to false
	private boolean integrityOK = false;

	// Step 2.2
	private void checkCapacity(int capacity) {
		if (capacity > MAX_CAPACITY) {
			// Throw a runtime exception
			throw new IllegalStateException("Attempt to create a bag whose capacity exceeds the allowed maximum.");
		}
	}

	/**
	 * Throw an exception if the integrity flag is false
	 */
	private void checkIntegrity() {
		if (!this.integrityOK) {
			// Throw a runtime exception
			throw new IllegalStateException("The Bag object is corrupt.");
		}
	}

	/**
	 * Constructor that takes the capacity as an argument
	 * @param capacity
	 */
	public AList(int capacity) {
		// Step 3.3 - Add security update to the constructor
		this.integrityOK = false;

		// Step 2.3
		// check that the desired capacity is below the max allowed
		checkCapacity(capacity);

		// NOTE THE METHOD FOR INITIALIIZING A GENERIC ARRAY
		// Also add SuppressWarnings for below  the cast is safe since the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] data = (T[])(new Object[capacity]);
		this.list = data;

		// initialize the number of entries
		this.numberOfEntries = 0;

		// Step 3.3 - set integrityOK to true
		// Assert: Integrity is OK
		this.integrityOK = true;

	}

	/**
	 * No-Arg Constructor
	 */
	public AList() {
		this(DEFAULT_CAPACITY);
	}

	private boolean isFull() {
		return this.numberOfEntries >= this.list.length;
	}



	private void doubleCapacity() {

		int newLength = 2 * list.length;
		checkCapacity(newLength);

		// Steps to copy
		// 1. Create a new container of the needed size
		// 2. Copy all elements from the original container to the new

		// 1. Create a new container based on the actual number of objects in the Bag.
		// OK to suppress the warning since the new array is filled with null values
		@SuppressWarnings("unchecked")
		T[] data = (T[])(new Object[newLength]);

		// 2. Copy the values from bag to result
		for (int i=0; i<this.numberOfEntries; i++) {
			// NOTE: We do not make deep copies of the objects contained in the array
			data[i] = list[i]; 
		}

		// Copy the reference back to our bag data.
		this.list = data;

	} // end doubleCapacity

	@Override
	public void add(T newEntry) {
		// Step 3.4 - Update for integrity security
		this.checkIntegrity();

		// Step 6.2 - Check for array full
		if (this.isFull()) {
			doubleCapacity();
		} // end if


		// Assertion: result is true and there is room in the array
		// add the new object at index numberOfEntries
		this.list[this.numberOfEntries] = newEntry;

		// increment numberOfEntries
		this.numberOfEntries++;
	}

	@Override
	public void add(int newPosition, T newEntry) {
		// check the bounds of newPosition
				if (newPosition < 0 || newPosition > this.getLength()) {
					throw new IndexOutOfBoundsException();
				}
				
				// check integrity since we need to access the array
				this.checkIntegrity();
				
				
				// check the capacity
				if (this.isFull()) {
					doubleCapacity();
				} 
				
				
				// make room for the new entry in the array.
				for (int dst=this.numberOfEntries; dst>newPosition; dst--) {
					this.list[dst] = this.list[dst-1];
				}
				
				// add the new entry here.
				this.list[newPosition] = newEntry;
				
				// Increment number of entries
				this.numberOfEntries++;

	}

	@Override
	public T remove(int givenPosition) {
		// Integrity check
		this.checkIntegrity();

		// Initialize the result assume failure
		boolean result = false;

		// Find the first index of the of the object.
		// NOTE: if the array is empty then the loop will not run
		int index = -1; // use a value that cannot be a valid index
		for (int i=0; (index<0 && i<this.numberOfEntries); i++) {
			if(this.getEntry(givenPosition) == (this.list[i])) {   // entry == index of list then set index to i
				index = i;
			}
		}

		// if index >= 0 then we can do the swap and remove
		if (index >= 0) {
			// Move the last item here
			this.list[index] = this.list[this.numberOfEntries-1];

			// set last item to null
			this.list[this.numberOfEntries-1] = null;

			// decrement
			this.numberOfEntries--;

			// set the result to true
			result = true;
		}

		return list[index];
	}

	@Override
	public void clear() {
		while () {
			

	}

	@Override
	public T replace(int givenPosition, T newEntry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getEntry(int givenPosition) {
		// core of this method.		
				T result = this.list[givenPosition];
				return result;
	}

	@Override
	public T[] toArray() {
		// Step 3.5 - Update for integrity security
				this.checkIntegrity();

				// Steps to copy
				// 1. Create a new container of the needed size
				// 2. Copy all elements from the original container to the new

				// 1. Create a new container based on the actual number of objects in the Bag.
				// OK to suppress the warning since the new array is filled with null values
				@SuppressWarnings("unchecked")
				T[] result = (T[])(new Object[this.numberOfEntries]);

				// 2. Copy the values from bag to result
				for (int i=0; i<result.length; i++) {
					// NOTE: We do not make deep copies of the objects contained in the array
					result[i] = list[i]; 
				}

				// return the result
				return result;
	}

	@Override
	public boolean contains(T anEntry) {
		
				// Method 2
				// Check integrity first
				this.checkIntegrity();

				// Loop through the array and compare anEntry with each item
				boolean found = false;
				for (int i=0; (!found && i<this.numberOfEntries); i++) {
					found = anEntry.equals(this.list[i]);
				}

				// return the count
				return found;

		
	}

	@Override
	public int getLength() {
		return this.numberOfEntries;
	}

	@Override
	public boolean isEmpty() {
		return (this.numberOfEntries==0);
		
	}

}
