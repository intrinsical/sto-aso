/*******************************************************************************
 * Copyright (C) 2015, 2018 Dave Kor
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.kor.admiralty.beans;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.kor.admiralty.io.Datastore;

public class Solver {

	protected static final Comparator<HasScore> COMPARATOR = new ScoreComparator();
	protected static final double WEIGHT_POSITIVE = 1.0d;
	protected static final double WEIGHT_NEGATIVE = 3.0d;

	public static AssignmentSolution solve(Assignment assignment, Ship ship1, Ship ship2, Ship ship3) {
		List<Ship> ships = new ArrayList<Ship>();
		int currentIndex = -1;
		int index1 = -1;
		int index2 = -1;
		int index3 = -1;
		if (ship1 != null) {
			ships.add(ship1);
			index1 = ++currentIndex;
		}
		if (ship2 != null) {
			ships.add(ship2);
			index2 = ++currentIndex;
		}
		if (ship3 != null) {
			ships.add(ship3);
			index3 = ++currentIndex;
		}
		return computeAssignmentSolution(assignment, ships, index1, index2, index3);
	}

	public static List<CompositeSolution> solve(Assignment assignment1, Assignment assignment2, Assignment assignment3, List<Ship> ships, int numSolutions) {
		List<AssignmentSolution> solutions1 = solveAssignment(assignment1, ships, numSolutions);
		List<AssignmentSolution> solutions2 = solveAssignment(assignment2, ships, numSolutions);
		List<AssignmentSolution> solutions3 = solveAssignment(assignment3, ships, numSolutions);

		TreeSet<CompositeSolution> solutions = new TreeSet<CompositeSolution>(COMPARATOR);
		for (int index1 = 0; index1 < solutions1.size(); index1++) {
			AssignmentSolution solution1 = solutions1.get(index1);

			if (solutions2.isEmpty()) {
				CompositeSolution solution = new CompositeSolution(solution1);
				solutions.add(solution);
			} else {
				for (int index2 = index1; index2 < solutions2.size(); index2++) {
					AssignmentSolution solution2 = solutions2.get(index2);

					if (solutions3.isEmpty()) {
						if (isValid(solution1, solution2)) {
							CompositeSolution solution = new CompositeSolution(solution1, solution2);
							solutions.add(solution);
						}
					} else {
						for (int index3 = index2; index3 < solutions3.size(); index3++) {
							AssignmentSolution solution3 = solutions3.get(index3);
							if (isValid(solution1, solution2, solution3)) {
								CompositeSolution solution = new CompositeSolution(solution1, solution2, solution3);
								solutions.add(solution);
							}
						}
					}
				}
			}
		}
		
		List<CompositeSolution> top = getTopSolutions(solutions, numSolutions);
		for (CompositeSolution solution : top) {
			solution.setShips(ships);
		}
		return top;
	}

	protected static boolean isValid(AssignmentSolution... solutions) {
		BitSet ships = new BitSet();
		for (AssignmentSolution solution : solutions) {
			if (solution == null)
				continue;

			int indexes[] = solution.getShipIndexes();
			for (int index : indexes) {
				if (index < 0)
					continue;

				if (ships.get(index)) {
					// This ship has already been used!
					return false;
				}
				ships.set(index);
			}
		}
		return true;
	}

	protected static <S extends HasScore> List<S> getTopSolutions(SortedSet<S> solutions, int numSolutions) {
		int num = Math.min(numSolutions, solutions.size());
		List<S> top = new ArrayList<S>(solutions);
		solutions.clear();
		return top.subList(0,  num);
	}
	
	public static List<AssignmentSolution> solveAssignment(Assignment assignment, List<Ship> ships, int numSolutions) {
		if (assignment == null)
			return Collections.emptyList();

		int numShips = ships.size();
		SortedSet<AssignmentSolution> solutions = new TreeSet<AssignmentSolution>(COMPARATOR);
		for (int slot3 = -1; slot3 < numShips; slot3++) {
			// Ship ship3 = slot3 < 0 ? null : ships.get(slot3);
			for (int slot2 = -1; slot2 < numShips; slot2++) {
				if ((slot2 <= slot3) && (slot3 != -1))
					continue;
				// Ship ship2 = slot2 < 0 ? null : ships.get(slot2);
				for (int slot1 = -1; slot1 < numShips; slot1++) {
					if ((slot1 <= slot2))
						continue;
					// Ship ship1 = slot1 < 0 ? null : ships.get(slot1);
					AssignmentSolution solution = computeAssignmentSolution(assignment, ships, slot1, slot2, slot3);
					solutions.add(solution);
				}
			}
		}
		return getTopSolutions(solutions, numSolutions);
	}
	
	public static AssignmentSolution computeAssignmentSolution(Assignment assignment, List<Ship> ships, int index1,
			int index2, int index3) {
		Ship ship1 = index1 >= 0 ? ships.get(index1) : null;
		Ship ship2 = index2 >= 0 ? ships.get(index2) : null;
		Ship ship3 = index3 >= 0 ? ships.get(index3) : null;
		AssignmentSolution solution = new AssignmentSolution(assignment.getEventCritRate(), index1, index2, index3);
		if (ship1 != null) {
			solution.addEng(ship1.getEng());
			solution.addTac(ship1.getTac());
			solution.addSci(ship1.getSci());
			SpecialAbility ability = ship1.getSpecialAbility();
			ability.procShip(solution, ship1, ship2);
			ability.procShip(solution, ship1, ship3);
		}
		if (ship2 != null) {
			solution.addEng(ship2.getEng());
			solution.addTac(ship2.getTac());
			solution.addSci(ship2.getSci());
			SpecialAbility ability = ship2.getSpecialAbility();
			ability.procShip(solution, ship2, ship1);
			ability.procShip(solution, ship2, ship3);
		}
		if (ship3 != null) {
			solution.addEng(ship3.getEng());
			solution.addTac(ship3.getTac());
			solution.addSci(ship3.getSci());
			SpecialAbility ability = ship3.getSpecialAbility();
			ability.procShip(solution, ship3, ship1);
			ability.procShip(solution, ship3, ship2);
		}
		if (ship1 != null) {
			ship1.getSpecialAbility().procAssignment(solution, assignment);
		}
		if (ship2 != null) {
			ship2.getSpecialAbility().procAssignment(solution, assignment);
		}
		if (ship3 != null) {
			ship3.getSpecialAbility().procAssignment(solution, assignment);
		}
		if (ship1 != null) {
			ship1.getSpecialAbility().procCriticals(solution, assignment);
		}
		if (ship2 != null) {
			ship2.getSpecialAbility().procCriticals(solution, assignment);
		}
		if (ship3 != null) {
			ship3.getSpecialAbility().procCriticals(solution, assignment);
		}
		
		int assignmentEng = solution.isIgnoreEventEng() ? assignment.getRequiredEng() : assignment.eng();
		int assignmentTac = solution.isIgnoreEventTac() ? assignment.getRequiredTac() : assignment.tac();
		int assignmentSci = solution.isIgnoreEventSci() ? assignment.getRequiredSci() : assignment.sci();
		//int assignmentCritChance = assignment.getTargetCritChance();
		int assignmentCritRate = assignment.getTargetCritRate();
		int eng = solution.getEng() - assignmentEng;
		int tac = solution.getTac() - assignmentTac;
		int sci = solution.getSci() - assignmentSci;
		int critRate = solution.computeCritRate(eng > 0 ? eng : 0, tac > 0 ? tac : 0, sci > 0 ? sci : 0) - assignmentCritRate;
		
		int absEng = Math.abs(eng);
		int absTac = Math.abs(tac);
		int absSci = Math.abs(sci);
		
		double score = 0d;
		/*/ Old Code
		if (assignmentCritChance == 0) {
			double scoreEng = absEng * (eng > 0 ? WEIGHT_POSITIVE : WEIGHT_NEGATIVE);
			double scoreTac = absTac * (tac > 0 ? WEIGHT_POSITIVE : WEIGHT_NEGATIVE);
			double scoreSci = absSci * (sci > 0 ? WEIGHT_POSITIVE : WEIGHT_NEGATIVE);
			score = (scoreEng + scoreTac + scoreSci) / (assignmentEng + assignmentTac + assignmentSci);
		}
		else {
			double scoreEng = absEng * (eng > 0 ? 0d : 10d);
			double scoreTac = absTac * (tac > 0 ? 0d : 10d);
			double scoreSci = absSci * (sci > 0 ? 0d : 10d);
			double scoreCritRate = Math.abs(critRate);
			score = (scoreEng + scoreTac + scoreSci + scoreCritRate) / (assignmentEng + assignmentTac + assignmentSci);
		}
		/*/ // New Code
		double scoreEng = absEng * (eng > 0 ? 0d : 10d);
		double scoreTac = absTac * (tac > 0 ? 0d : 10d);
		double scoreSci = absSci * (sci > 0 ? 0d : 10d);
		double scoreCritRate = Math.abs(critRate);
		score = (scoreEng + scoreTac + scoreSci + scoreCritRate) / (assignmentEng + assignmentTac + assignmentSci);
		//*/
		solution.setScore(score);
		return solution;
	}
	
	public static void main(String args[]) {
		Assignment assignment1 = new Assignment(10, 10, 10, 0, 10, 0, 5);
		Assignment assignment2 = new Assignment(35, 35, 90, 10, 0, 10, 5);
		Assignment assignment3 = new Assignment(50, 50, 50, 25, 25, 25, 5);
		
		List<Ship> ships = new ArrayList<Ship>(Datastore.getAllShips().values());
		Collections.shuffle(ships);
		ships = ships.subList(0, 50);
		
		List<CompositeSolution> solutions = Solver.solve(assignment1, assignment2, assignment3, ships, 10);
		for (int i = 0; i < solutions.size(); i++) {
			CompositeSolution solution = solutions.get(i);
			System.out.println(solution);
		}
	}

}
