package me.dibdin.adventofcode.year2021;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import me.dibdin.adventofcode.AbstractChallenge;

/**
 * Advent of Code Challenge 2021 - Day 16: Packet Decoder.
 * https://adventofcode.com/2021/day/16
 */
public class Day16 extends AbstractChallenge {

    // the puzzle data
    Packet transmissionPacket = null;
    boolean readyToSolve = false;

    /**
     * Class to represent the packets in the data
     */
    abstract class Packet {
        private int version;
        private int type;

        /**
         * Contructor
         * 
         * @param version the packet version number
         * @param type    the packet type
         */
        Packet(int version, int type) {
            this.version = version;
            this.type = type;
        }

        /**
         * Returns the packet type
         * 
         * @return the packet type
         */
        int getType() {
            return type;
        }

        /**
         * Get the value of the packet
         * 
         * @return value of the packet
         */
        abstract long getValue();
    }

    /**
     * Represents a literal packet, i.e. a packet which contains a single literal
     * number value
     */
    class LiteralPacket extends Packet {
        private long value;

        /**
         * Contructor
         * 
         * @param version the packet version number
         * @param type    the packet type
         */
        LiteralPacket(int version, int type) {
            super(version, type);
        }

        /**
         * Set the literal value
         * 
         * @param value the value to set
         */
        void setValue(long value) {
            this.value = value;
        }

        /**
         * Get the literal value
         */
        long getValue() {
            return value;
        }
    }

    /**
     * Represents an operator packet, i.e. a packet which performs and operation on
     * it's sub-packets
     */
    class OperatorPacket extends Packet {
        private ArrayList<Packet> subPackets;

        /**
         * Contructor
         * 
         * @param version the packet version number
         * @param type    the packet type
         */
        OperatorPacket(int version, int type) {
            super(version, type);
            subPackets = new ArrayList<Packet>();
        }

        /**
         * Adds a new sub packet to the list
         * 
         * @param p the packet to add
         */
        void addSubPacket(Packet p) {
            subPackets.add(p);
        }

        /**
         * Returns a list of the sub packets in this packet
         * 
         * @return the list
         */
        List<Packet> getSubPackets() {
            return new ArrayList<Packet>(subPackets);
        }

        /**
         * Calculates the value of this packet
         * 
         * @return the value of this packet
         */
        long getValue() {
            long result = -1;

            switch (this.getType()) {
                case 0: // sum
                    result = 0;
                    for (Packet p : subPackets) {
                        result += p.getValue();
                    }
                    break;
                case 1: // product
                    result = 1;
                    for (Packet p : subPackets) {
                        result *= p.getValue();
                    }
                    break;
                case 2: // minimum
                    result = Long.MAX_VALUE;
                    for (Packet p : subPackets) {
                        result = Long.min(result, p.getValue());
                    }
                    break;
                case 3: // maximum
                    result = Long.MIN_VALUE;
                    for (Packet p : subPackets) {
                        result = Long.max(result, p.getValue());
                    }
                    break;
                case 4:
                    // this shouldn't happen!
                    break;
                case 5: // greater than
                    if (subPackets.size() == 2) {
                        result = (subPackets.get(0).getValue() > subPackets.get(1).getValue()) ? 1 : 0;
                    }
                    break;
                case 6: // less than
                    if (subPackets.size() == 2) {
                        result = (subPackets.get(0).getValue() < subPackets.get(1).getValue()) ? 1 : 0;
                    }
                    break;
                case 7: // equal to
                    if (subPackets.size() == 2) {
                        result = (subPackets.get(0).getValue() == subPackets.get(1).getValue()) ? 1 : 0;
                    }
                    break;
            }

            return result;
        }
    }

    // contants for the length of various parts of the message
    final int VERSION_BIT_LENGTH = 3;
    final int TYPE_BIT_LENGTH = 3;
    final int SINGLE_BIT_LENGTH = 1;
    final int LENGTH_TYPE_BIT_LENGTH = 1;
    final int LENGTH_TYPE_TOTAL_BITS_BIT_LENGTH = 15;
    final int LENGTH_TYPE_NUMBER_OF_PACKETS_BIT_LENGTH = 11;
    final int NIBBLE_LENGTH = 4;

    // mapping of hex characters to binary strings, padded to 4 bits
    Map<Character, String> hexToBinary = Map.ofEntries(
            Map.entry('0', "0000"),
            Map.entry('1', "0001"),
            Map.entry('2', "0010"),
            Map.entry('3', "0011"),
            Map.entry('4', "0100"),
            Map.entry('5', "0101"),
            Map.entry('6', "0110"),
            Map.entry('7', "0111"),
            Map.entry('8', "1000"),
            Map.entry('9', "1001"),
            Map.entry('A', "1010"),
            Map.entry('B', "1011"),
            Map.entry('C', "1100"),
            Map.entry('D', "1101"),
            Map.entry('E', "1110"),
            Map.entry('F', "1111"));

    /**
     * Constructor.
     */
    public Day16() {
        super("Packet Decoder", 2021, 16);
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if (!readyToSolve) {
            throw new IllegalStateException("No puzzle input set");
        }

        return sumVersionNumbers(transmissionPacket);
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (!readyToSolve) {
            throw new IllegalStateException("No puzzle input set");
        }

        return transmissionPacket.getValue();
    }

    /**
     * Sums all the version numbers from each of packets and sub packets
     * 
     * @param p the current packet
     * @return the sum of the version numbers
     */
    private long sumVersionNumbers(Packet p) {
        // start with this packet's version number
        long result = p.version;

        // add in the version numbers of all the sub-packets, recursively
        if (p instanceof OperatorPacket) {
            OperatorPacket op = (OperatorPacket) p;
            for (int i = 0; i < op.getSubPackets().size(); i++) {
                result += sumVersionNumbers(op.getSubPackets().get(i));
            }
        }

        return result;
    }

    /**
     * Reads a number from the iterator, read a binary bits and returned as a
     * decimal int
     * 
     * @param iterator  the iterator to read from
     * @param bitLength the number of bits to read for the number
     * @return the decimal integer
     */
    private int readBinaryInt(StringCharacterIterator iterator, int bitLength) {
        return Integer.parseInt(readBinaryString(iterator, bitLength), 2);
    }

    /**
     * Reads a string from the iterator, for the specified number of bits
     * 
     * @param iterator  the iterator to read from
     * @param bitLength the number of bits to read for the number
     * @return the decimal integer
     */
    private String readBinaryString(StringCharacterIterator iterator, int bitLength) {
        StringBuilder sb = new StringBuilder(bitLength);
        for (int i = 0; i < bitLength; i++) {
            sb.append(iterator.next());
        }
        return sb.toString();
    }

    /**
     * Read a packet from the iterator. If this is an operator packet, also reads
     * all the sub packets
     * 
     * @param iterator the iterator to read from
     * @return the Packet
     */
    private Packet readPacket(StringCharacterIterator iterator) {
        Packet returnPacket = null;

        // read the version and type
        int version = readBinaryInt(iterator, VERSION_BIT_LENGTH);
        int type = readBinaryInt(iterator, TYPE_BIT_LENGTH);

        if (type == 4) {
            // this is a literal value packet
            LiteralPacket literalPacket = new LiteralPacket(version, type);

            // read the value string, one nibble at the time
            StringBuilder sb = new StringBuilder();
            boolean hasMoreNibbles = true;
            while (hasMoreNibbles) {
                hasMoreNibbles = readBinaryInt(iterator, SINGLE_BIT_LENGTH) == 1 ? true : false;
                sb.append(readBinaryString(iterator, NIBBLE_LENGTH));
            }
            literalPacket.setValue(Long.parseLong(sb.toString(), 2));

            returnPacket = literalPacket;
        } else {
            // this is an operator packet
            OperatorPacket operatorPacket = new OperatorPacket(version, type);

            // read the length of the packet
            int lengthType = readBinaryInt(iterator, LENGTH_TYPE_BIT_LENGTH);
            int length;

            if (lengthType == 0) {
                length = readBinaryInt(iterator, LENGTH_TYPE_TOTAL_BITS_BIT_LENGTH);
            } else {
                length = readBinaryInt(iterator, LENGTH_TYPE_NUMBER_OF_PACKETS_BIT_LENGTH);
            }

            // read the sub-packets and add them to this operator packet
            int iteratorIndex = iterator.getIndex();
            int packetCount = 0;
            while (((lengthType == 0) && ((iterator.getIndex() - iteratorIndex) < length)) ||
                    ((lengthType == 1) && (packetCount < length))) {
                // read the next sub-packet
                operatorPacket.addSubPacket(readPacket(iterator));
                packetCount++;
            }

            returnPacket = operatorPacket;
        }

        // this packet and all it's sub-packets have been read
        return returnPacket;
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this
     * challenge.
     */
    public void setPuzzleInput(Stream<String> input) {

        // convert the hexidecimal input string into a binary input string
        String hexInput = input.iterator().next();
        StringBuilder sb = new StringBuilder(hexInput.length() * 4 + 1);
        sb.append('S'); // offset input so there's no need to call string character iterator first

        for (int i = 0; i < hexInput.length(); i++) {
            sb.append(hexToBinary.get(hexInput.charAt(i)));
        }

        // read the outer packet, and all sub packets
        transmissionPacket = readPacket(new StringCharacterIterator(sb.toString()));

        readyToSolve = true;
    }
}