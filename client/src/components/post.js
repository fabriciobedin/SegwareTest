import React from 'react'
import PostItem from './postItem';

const Post = (props) => {
  const PostList = props.posts.map((post) => {
    return <PostItem
      upVote={props.upVote}
      downVote={props.downVote}
      post={post}
      key={post.id} />
  })

  return (
    <div>
      {PostList}
    </div>
  )
}

export default Post