
// matches ReviewWithBookTitleDto from backend
export interface Review {
    id: number;
    reviewerUsername: string;
    bookIsbn: string;
    bookTitle: string;
    rating: number;
    reviewText: string;
    createdAt: string; // iso string from localdatetime
    dateFinished: string | null
}

export interface HomeResponse {
    authenticated: boolean;
    username: string | null;
    landingPageMessage: string;
    recentReviews: Review[];
    totalReviews: number;
}