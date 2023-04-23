# Example games we could implement! (3 total)

Describe in detail at least three example games from your genre or game variations that differ
significantly in their functionality. These example games should help clarify the abstractions you
plan to use in your design, so explain why you chose them and clearly identify their functional
commonalities and differences. Note, they do not need to be the final ones you implement but
certainly could be.

Game #1: Tic Tac Toe
Tic Tac Toe is a simple game where there are two players placing their pieces on a 3x3 grid. The
goal of the game is to place 3 of their pieces in a row, which can be vertically, horizontally, or
diagonally. This game is simple and a great goal for the first sprint because pieces cannot move
after being placed. They can also be placed anywhere on the grid that is available so there are less
rules to define.

Game #2: Chess
Chess is a two-player, strategic board game with a long history that dates back over a thousand
years. It is widely regarded as one of the most intellectually challenging and enduring games in
existence. The game is played on an 8x8 grid board, which consists of 64 squares alternating in
color (usually black and white or a similar contrasting combination).

The team chose this game because it is a classic and perhaps the most well known strategy game in
the world. It also involves a grid-like structure that can help set up a basic structure for all
board-based games. It is also a two player game so it starts with a rather simple game competition
structure and easily defined victory parameters (though hard to check for), however allows for lots
of extension later like AI opponents or networked multiplayer.

Game #3: Monopoly
Monopoly is a popular board game that includes both a board setup with pieces and cards to dictate
game play. Monopoly is different then chess and monopoly where instead the move options are limited
to just forward the number shown on the dice. This leaves most of the strategy to optional decisions
that can be made on any turn - whether to buy a property or not, whether to buy a house, what to
mortgage, who to target with a community chest card… Monopoly is a classic board game with a more
complex turnplay and rule system. This is why it is a great final goal for us because if we can
implement monopoly, we can likely implement most of the other common board games. Implementing chess
and tic-tac toe will establish some prerequisites to help control piece movement, and then the card
rules will be a new feature that can extend to many other games.

