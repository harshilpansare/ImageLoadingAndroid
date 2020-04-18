# ImageLoadingAndroid

About the code:

1. Implemented clean architecture.
2. Implemented dependency injection as static methods as of now as it is not in the scope of the problem.
3. Paging has been implemented using the PagedList jetpack component.
4. Image loading has been implemented using two approaches, i.e., with Glide, without Glide(Glide2 module)


About PagedList Implementation:

1. Pagination logic abstracted in a separate module which is another abstraction over PagedList components.
2. Pagination can be easily implemented by any paginated network request by extending to 4 classes, i.e., PaginationEntity, PaginationRecyclerAdapter, PaginatedViewModel, PaginatedDataSource.
3. Which makes pagination implementation completely hassle-free.


About Glide2 Custom Implementation:

1. Even though we can easily implement it glide, implemented it as a separate library for educational purpose and to address for glide limitations.
2. Glide2 implements its own separate in-memory cache(Lru cache) and local cache(db and file storage)
3. The local cache is not implemented in the code yet but you can get the idea.
4. The current Glide2 implementation relies on in-memory cache only and hence a little bit slower. The reason being, we need the local cache to quickly fetch the image instead of downloading again.5. The current implementation is "maxItems" dependent and not "maxMemory" dependent. 
5. It can be optimized further by automatically deriving how much memory to use for caching by checking free memory.
