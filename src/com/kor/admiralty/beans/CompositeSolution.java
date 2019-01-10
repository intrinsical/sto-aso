/*******************************************************************************
 * Copyright (C) 2015, 2019 Dave Kor
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

import java.util.List;

public class CompositeSolution implements HasScore {
	
	protected AssignmentSolution solutions[];
	protected double score;
	
	public CompositeSolution(AssignmentSolution ... solutions) {
		this.solutions = solutions;
		for (AssignmentSolution solution : solutions) {
			score += solution.getScore();
		}
	}
	
	@Override
	public void setShips(List<Ship> ships) {
		for (AssignmentSolution solution : solutions) {
			solution.setShips(ships);
		}
	}
	
	@Override
	public double getScore() {
		return score;
	}
	
	public AssignmentSolution[] getSolutions() {
		return solutions;
	}
	
	public AssignmentSolution getSolution(int index) {
		if (index < 0) return null;
		if (index >= solutions.length) return null; 
		return solutions[index];
	}
	
	public int size() {
		return solutions.length;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CompositeSolution[").append(score).append("](\n");
		for (AssignmentSolution solution : solutions) {
			sb.append("\t").append(solution).append("\n");
		}
		sb.append(")");
		return sb.toString();
	}

}
