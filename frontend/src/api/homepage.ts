import type { HomeResponse } from "../types/review";

export async function getHomeData(): Promise<HomeResponse> {
    const res = await fetch("/api/home");
    if (!res.ok) throw new Error("Failed to load homepage womp womp");
    return res.json();
}