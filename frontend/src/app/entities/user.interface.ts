export default interface User {
  id: number;
  name: string;
  role: 'User' | 'Trainer';
}