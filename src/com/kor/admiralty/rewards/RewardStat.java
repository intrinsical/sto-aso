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
package com.kor.admiralty.rewards;

import com.kor.admiralty.beans.Assignment;
import com.kor.admiralty.beans.AssignmentSolution;
import com.kor.admiralty.ui.resources.Strings;

public class RewardStat implements Reward {
	
	protected int rewardEng;
	protected int rewardTac;
	protected int rewardSci;
	protected double rewardCritRating;
	
	public RewardStat(int rewardEng, int rewardTac, int rewardSci) {
		this(rewardEng, rewardTac, rewardSci, 0);
	}

	public RewardStat(int rewardEng, int rewardTac, int rewardSci, double rewardCritRating) {
		this.rewardEng = rewardEng;
		this.rewardTac = rewardTac;
		this.rewardSci = rewardSci;
		this.rewardCritRating = rewardCritRating;
	}

	@Override
	public void apply(Assignment assignment, AssignmentSolution solution) {
		solution.addEng(rewardEng);
		solution.addTac(rewardTac);
		solution.addSci(rewardSci);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj instanceof RewardStat) {
			RewardStat reward = (RewardStat)obj;
			if (rewardEng != reward.rewardEng) return false;
			if (rewardTac != reward.rewardTac) return false;
			if (rewardSci != reward.rewardSci) return false;
			if (rewardCritRating != reward.rewardCritRating) return false;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return Strings.toFunctionString(this, String.format("rewardEng: %d, rewardTac: %d, rewardSci: %d, rewardCritRating: %f", rewardEng, rewardTac, rewardSci, rewardCritRating));
	}
	
}
