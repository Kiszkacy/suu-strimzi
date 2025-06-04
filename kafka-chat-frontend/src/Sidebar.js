import React from 'react';
import './Sidebar.css';

const Sidebar = ({ chats, selectedChat, onSelectChat }) => {
  return (
    <div className="sidebar">
      <h2>Chats</h2>
      <ul>
        {chats.map((chat, idx) => (
          <li
            key={idx}
            className={chat === selectedChat ? 'active' : ''}
            onClick={() => onSelectChat(chat)}
          >
            {chat}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Sidebar;
