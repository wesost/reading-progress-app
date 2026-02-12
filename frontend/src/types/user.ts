export interface UserDto {
  username: string;
  email: string;
  password?: string;
  role?: string;
}

export interface UserResponseDto {
  username: string;
  email: string;
  role?: string;
}