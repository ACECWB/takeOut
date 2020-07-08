package takeout.itf;

import takeout.util.BaseException;

import java.util.List;

import takeout.model.*;
public interface IReviewManager {
	public List<Review> loadAllReviews(String businessid)throws BaseException;
}
