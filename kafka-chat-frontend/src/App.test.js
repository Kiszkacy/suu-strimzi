import {render, screen} from '@testing-library/react';
import App from './App';

test('renders chat input fields and send button', () => {
  render(<App/>);

  const usernameInput = screen.getByPlaceholderText(/enter your name/i);
  expect(usernameInput).toBeInTheDocument();

  const messageInput = screen.getByPlaceholderText(/type your message/i);
  expect(messageInput).toBeInTheDocument();

  const sendButton = screen.getByRole('button', {name: /send/i});
  expect(sendButton).toBeInTheDocument();
});

test('renders send button', () => {
  render(<App/>);

  const sendButton = screen.getByRole('button', {name: /send/i});
  expect(sendButton).toBeInTheDocument();
});
